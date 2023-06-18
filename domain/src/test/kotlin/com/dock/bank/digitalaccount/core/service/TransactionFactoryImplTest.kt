package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.utils.buildAccount
import com.dock.bank.digitalaccount.ports.spi.AccountDatabasePort
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigInteger
import java.util.*

@ExtendWith(MockKExtension::class)
@DisplayName("transaction factory tests")
class TransactionFactoryImplTest {

    @MockK
    private lateinit var accountPersistence: AccountDatabasePort

    @InjectMockKs
    private lateinit var transactionFactory: TransactionFactoryImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    @DisplayName(value = "should return a transaction with success")
    fun `should return a transaction with success`() {
        runBlocking {
            val amount = BigInteger.valueOf(100)
            val type = TransactionType.CREDIT
            val accountId = UUID.randomUUID()
            val fakeAccount = buildAccount()

            coEvery { accountPersistence.get(any()) } returns Optional.of(fakeAccount)

            val transaction = transactionFactory.generateTransaction(
                amount = amount,
                type = type,
                accountId = accountId
            )

            coVerify (exactly = 1) { accountPersistence.get(accountId) }

            assertEquals(amount, transaction.amount)
            assertEquals(type, transaction.type)
            assertEquals(fakeAccount.id, transaction.account.id)
            assertEquals(fakeAccount.holder.name, transaction.account.holder.name)
            assertEquals(fakeAccount.holder.cpf, transaction.account.holder.cpf)
            assertEquals(fakeAccount.status, transaction.account.status)
            assertEquals(fakeAccount.branch, transaction.account.branch)
            assertEquals(fakeAccount.number, transaction.account.number)
        }
    }

    @Test
    @DisplayName("should throw an exception trying to build a transaction")
    fun `should throw an exception trying to build a transaction`() {
        runBlocking {
            val amount = BigInteger.valueOf(100)
            val type = TransactionType.CREDIT
            val accountId = UUID.randomUUID()

            coEvery { accountPersistence.get(any()) } returns Optional.empty()

            val error = assertThrows<ResourceNotFoundException> {
                transactionFactory.generateTransaction(
                    amount = amount,
                    type = type,
                    accountId = accountId
                )
            }
            assertEquals("Account not found.", error.message)
        }
    }
}