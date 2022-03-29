package com.dock.bank.digitalaccount.core.usecase

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.port.adapter.AccountGenerator
import com.dock.bank.digitalaccount.core.port.persistence.AccountPersistence
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import com.dock.bank.digitalaccount.utils.buildAccount
import com.dock.bank.digitalaccount.utils.buildHolder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
@DisplayName("account use case tests")
class AccountUseCaseImplTest {

    @MockK
    private lateinit var holderPersistence: HolderPersistence

    @MockK
    private lateinit var accountPersistence: AccountPersistence

    @MockK
    private lateinit var accountGenerator: AccountGenerator

    @InjectMockKs
    private lateinit var accountUseCase: AccountUseCaseImpl

    private val accountSlot = slot<Account>()
    private val holderSlot = slot<Holder>()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    @DisplayName("should create an account with success")
    fun `should create an account with success`() {
        runBlocking {
            val holderCpf = "00000000000"

            val fakeHolder = buildHolder()
            val fakeAccount = buildAccount()

            coEvery { holderPersistence.findByCpf(any()) } returns Optional.of(fakeHolder)
            coEvery { accountPersistence.create(any()) } returns fakeAccount
            coEvery { accountGenerator.generateAccount(any()) } returns fakeAccount

            accountUseCase.create(holderCpf)

            coVerify (exactly = 1) { holderPersistence.findByCpf(holderCpf) }

            coVerify (exactly = 1) { accountPersistence.create(capture(accountSlot)) }
            assertEquals(fakeAccount.id, accountSlot.captured.id)
            assertEquals(fakeAccount.balance, accountSlot.captured.balance)
            assertEquals(fakeAccount.branch, accountSlot.captured.branch)
            assertEquals(fakeAccount.status, accountSlot.captured.status)
            assertEquals(fakeAccount.holder.id, accountSlot.captured.holder.id)
            assertEquals(fakeAccount.holder.cpf, accountSlot.captured.holder.cpf)
            assertEquals(fakeAccount.holder.name, accountSlot.captured.holder.name)

            coVerify (exactly = 1) { accountGenerator.generateAccount(capture(holderSlot))}
            assertEquals(fakeHolder.id, holderSlot.captured.id)
            assertEquals(fakeHolder.cpf, holderSlot.captured.cpf)
            assertEquals(fakeHolder.name, holderSlot.captured.name)
        }
    }

    @Test
    @DisplayName("should get an account with success")
    fun `should get an account with success`() {
        runBlocking {
            val id = UUID.randomUUID()
            val fakeAccount = buildAccount()

            coEvery { accountPersistence.get(any()) } returns Optional.of(fakeAccount)

            accountUseCase.get(id)

            coVerify(exactly = 1) { accountPersistence.get(id) }

        }
    }

    @Test
    @DisplayName("should throw an exception trying to get an account")
    fun `should throw an exception trying to get an account`() {
        runBlocking {
            val id = UUID.randomUUID()

            coEvery { accountPersistence.get(any()) } returns Optional.empty()

            val error = assertThrows<ResourceNotFoundException> {
                accountUseCase.get(id)
            }
            assertEquals("Account not found.", error.message)
        }
    }
}

