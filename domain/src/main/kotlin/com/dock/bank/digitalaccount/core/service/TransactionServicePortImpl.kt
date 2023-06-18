package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.core.exception.LimitExceededException
import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.ports.api.TransactionServicePort
import com.dock.bank.digitalaccount.ports.spi.AccountDatabasePort
import com.dock.bank.digitalaccount.ports.spi.TransactionDatabasePort

import com.dock.bank.digitalaccount.utils.DateUtils
import java.time.LocalDate
import java.util.*

class TransactionServicePortImpl(
    private val transactionDatabasePort: TransactionDatabasePort,
    private val accountDatabasePort: AccountDatabasePort
) : TransactionServicePort {

    override suspend fun create(transaction: Transaction): Transaction {

        transaction.account.validateAccountIsDisabled()
        transaction.account.validateAccountIsBlocked()

        return if (TransactionType.DEBIT == transaction.type) {

            val dailyDebitSum = getDailyDebitTransactionSum(
                date = DateUtils.getLocalDate(transaction.createdDate),
                account = transaction.account,
                TransactionType.DEBIT
            ).plus(transaction.amount.intValueExact())

            if (dailyDebitSum > transaction.account.withdrawalLimit.intValueExact()) {
                throw LimitExceededException(message = "Account with exceeded limit.")
            }

            val account = transaction.accountWithdraw()
            val newTransaction = transaction.copy(account = account)

            accountDatabasePort.create(account)
            transactionDatabasePort.create(newTransaction)

        } else {
            val account = transaction.accountDeposit()
            val newTransaction = transaction.copy(account = account)

            accountDatabasePort.create(account)
            transactionDatabasePort.create(newTransaction)
        }
    }

    override suspend fun getTransactions(
        accountId: UUID,
        startDate: LocalDate,
        finishDate: LocalDate
    ): List<Transaction> {
        val account = accountDatabasePort.get(accountId).orElseThrow {
            throw ResourceNotFoundException(message = "Account not found.")
        }
        return transactionDatabasePort.getTransactions(startDate, finishDate, account)
    }

    private suspend fun getDailyDebitTransactionSum(date: LocalDate, account: Account, type: TransactionType) =
        transactionDatabasePort.sumDailyDebitTransactionsAmountByAccount(date, account, type)
}