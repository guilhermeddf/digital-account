package com.dock.bank.digitalaccount.core.domain

import com.dock.bank.digitalaccount.core.exception.DomainException
import com.dock.bank.digitalaccount.core.utils.buildAccount
import com.dock.bank.digitalaccount.core.utils.buildTransaction
import io.mockk.MockKAnnotations
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigInteger

@ExtendWith(MockitoExtension::class)
@DisplayName("transaction domain tests")
class TransactionTest {

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    @DisplayName("should throw an exception when account with insufficient balance")
    fun `should throw an exception when account with insufficient balance`() {
        val fakeTransaction = buildTransaction(
            amount = BigInteger.valueOf(200),
            account = buildAccount(
                balance = BigInteger.valueOf(100)
            )
        )

        val error = assertThrows<DomainException> {
            fakeTransaction.accountWithdraw()
        }

        assertEquals("Insufficient balance.", error.message)
    }

    @Test
    @DisplayName("should deposit transaction amount with success")
    fun `should deposit transaction amount with success`() {
        val fakeTransaction = buildTransaction(
            amount = BigInteger.valueOf(100),
            account = buildAccount(
                balance = BigInteger.valueOf(1000)
            )
        )

        val newAccount = fakeTransaction.accountDeposit()

        assertEquals(BigInteger.valueOf(1100), newAccount.balance)
    }

    @Test
    @DisplayName("should withdrawal transaction amount with success")
    fun `should withdrawal transaction amount with success`() {
        val fakeTransaction = buildTransaction(
            amount = BigInteger.valueOf(100),
            type = TransactionType.DEBIT,
            account = buildAccount(
                balance = BigInteger.valueOf(1000)
            )
        )

        val newAccount = fakeTransaction.accountWithdraw()
        assertEquals(BigInteger.valueOf(900), newAccount.balance)
    }
}