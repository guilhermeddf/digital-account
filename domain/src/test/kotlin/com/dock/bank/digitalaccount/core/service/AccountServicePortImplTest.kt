package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.utils.buildAccount
import com.dock.bank.digitalaccount.core.utils.buildHolder
import com.dock.bank.digitalaccount.ports.api.AccountGeneratorServicePort
import com.dock.bank.digitalaccount.ports.spi.AccountDatabasePort
import com.dock.bank.digitalaccount.ports.spi.HolderDatabasePort
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
class AccountServicePortImplTest {

    @MockK
    private lateinit var holderDatabasePort: HolderDatabasePort

    @MockK
    private lateinit var accountDatabasePort: AccountDatabasePort

    @MockK
    private lateinit var accountGeneratorServicePort: AccountGeneratorServicePort

    @InjectMockKs
    private lateinit var accountServicePort: AccountServicePortImpl

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

            coEvery { holderDatabasePort.findByCpf(any()) } returns Optional.of(fakeHolder)
            coEvery { accountDatabasePort.create(any()) } returns fakeAccount
            coEvery { accountGeneratorServicePort.generateAccount(any()) } returns fakeAccount

            accountServicePort.create(holderCpf)

            coVerify (exactly = 1) { holderDatabasePort.findByCpf(holderCpf) }

            coVerify (exactly = 1) { accountDatabasePort.create(capture(accountSlot)) }
            assertEquals(fakeAccount.id, accountSlot.captured.id)
            assertEquals(fakeAccount.balance, accountSlot.captured.balance)
            assertEquals(fakeAccount.branch, accountSlot.captured.branch)
            assertEquals(fakeAccount.status, accountSlot.captured.status)
            assertEquals(fakeAccount.holder.id, accountSlot.captured.holder.id)
            assertEquals(fakeAccount.holder.cpf, accountSlot.captured.holder.cpf)
            assertEquals(fakeAccount.holder.name, accountSlot.captured.holder.name)

            coVerify (exactly = 1) { accountGeneratorServicePort.generateAccount(capture(holderSlot))}
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
            coEvery { accountDatabasePort.get(any()) } returns Optional.of(fakeAccount)

            accountServicePort.get(id)

            coVerify(exactly = 1) { accountDatabasePort.get(id) }

        }
    }

    @Test
    @DisplayName("should throw an exception trying to get an account")
    fun `should throw an exception trying to get an account`() {
        runBlocking {
            val id = UUID.randomUUID()
            coEvery { accountDatabasePort.get(any()) } returns Optional.empty()

            val error = assertThrows<ResourceNotFoundException> {
                accountServicePort.get(id)
            }
            assertEquals("Account not found.", error.message)
        }
    }
}

