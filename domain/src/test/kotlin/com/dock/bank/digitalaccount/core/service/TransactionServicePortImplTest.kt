package com.dock.bank.digitalaccount.core.service


import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.core.exception.LimitExceededException
import com.dock.bank.digitalaccount.core.utils.buildAccount
import com.dock.bank.digitalaccount.core.utils.buildTransaction
import com.dock.bank.digitalaccount.ports.spi.AccountDatabasePort
import com.dock.bank.digitalaccount.ports.spi.TransactionDatabasePort
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
import java.math.BigInteger
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
@DisplayName("transaction use case tests")
class TransactionServicePortImplTest {

    @MockK
    private lateinit var accountPersistence: AccountDatabasePort

    @MockK
    private lateinit var transactionPersistence: TransactionDatabasePort

    @InjectMockKs
    private lateinit var transactionUseCase: TransactionServicePortImpl

    private val transactionSlot = slot<Transaction>()


    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    @DisplayName("should throw an limit exception trying to save a transaction")
    fun `should throw an limit exception trying to save a transaction`() {
        runBlocking {
            val fakeTransaction = buildTransaction(
                type = TransactionType.DEBIT,
                amount = BigInteger.valueOf(100001),
                account = buildAccount(
                    balance = BigInteger.valueOf(1000000),
                )
            )
            val fakeAccount = buildAccount()

            coEvery { transactionPersistence.sumDailyDebitTransactionsAmountByAccount(any(), any(), any()) } returns 100000
            coEvery { transactionPersistence.create(any()) } returns fakeTransaction
            coEvery { accountPersistence.create(any()) } returns fakeAccount

            val error = assertThrows<LimitExceededException> {
                transactionUseCase.create(fakeTransaction)
            }

            assertEquals("Account with exceeded limit.", error.message)
        }
    }

    @Test
    @DisplayName("should save a transaction with success")
    fun `should save a transaction with success`() {
        runBlocking {
            val fakeTransaction = buildTransaction(
                type = TransactionType.DEBIT,
                account = buildAccount(balance = BigInteger.valueOf(1000000))
            )
            val fakeAccount = buildAccount()

            coEvery { transactionPersistence.sumDailyDebitTransactionsAmountByAccount(any(), any(), any()) } returns 100000
            coEvery { transactionPersistence.create(any()) } returns fakeTransaction
            coEvery { accountPersistence.create(any()) } returns fakeAccount

            transactionUseCase.create(fakeTransaction)

            coVerify(exactly = 1) { transactionPersistence.sumDailyDebitTransactionsAmountByAccount(
                LocalDate.now(),
                fakeTransaction.account,
                TransactionType.DEBIT)
            }

            coVerify (exactly = 1) { transactionPersistence.create(capture(transactionSlot)) }
            assertEquals(fakeTransaction.id, transactionSlot.captured.id)
            assertEquals(fakeTransaction.amount, transactionSlot.captured.amount)
            assertEquals(fakeTransaction.createdDate, transactionSlot.captured.createdDate)

            assertEquals(fakeTransaction.account.id, transactionSlot.captured.account.id)
            assertEquals(fakeTransaction.account.number, transactionSlot.captured.account.number)
            assertEquals(fakeTransaction.account.branch, transactionSlot.captured.account.branch)
            assertEquals(fakeTransaction.account.balance - fakeTransaction.amount,
                transactionSlot.captured.account.balance)
            assertEquals(fakeTransaction.account.status, transactionSlot.captured.account.status)

            assertEquals(fakeTransaction.account.holder.cpf, transactionSlot.captured.account.holder.cpf)
        }
    }

    @Test
    @DisplayName("should get a list transaction with success")
    fun`should get a list transaction with success`() {
        runBlocking {
            val fakeTransactions = listOf(buildTransaction())
            val fakeAccount = buildAccount()

            coEvery { accountPersistence.get(any()) } returns Optional.of(fakeAccount)
            coEvery { transactionPersistence.getTransactions(any(), any(), any()) } returns fakeTransactions

            val result = transactionUseCase.getTransactions(UUID.randomUUID(), LocalDate.now(), LocalDate.now())

            assertEquals(fakeTransactions[0].amount, result[0].amount)
            assertEquals(fakeTransactions[0].createdDate, result[0].createdDate)
            assertEquals(fakeTransactions[0].id, result[0].id)
            assertEquals(fakeTransactions[0].type, result[0].type)
            assertEquals(1, result.size)

        }
    }
}