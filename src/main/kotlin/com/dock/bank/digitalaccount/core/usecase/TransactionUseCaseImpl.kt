package com.dock.bank.digitalaccount.core.usecase

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.core.exceptions.LimitExceededException
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.port.adapter.TransactionUseCase
import com.dock.bank.digitalaccount.core.port.persistence.AccountPersistence
import com.dock.bank.digitalaccount.core.port.persistence.TransactionPersistence
import com.dock.bank.digitalaccount.utils.DateUtils
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class TransactionUseCaseImpl(
    private val transactionPersistence: TransactionPersistence,
    private val accountPersistence: AccountPersistence
) : TransactionUseCase {

    override suspend fun create(transaction: Transaction): Transaction {

        transaction.account.validateAccountIsDisabled()
        transaction.account.validateAccountIsBlocked()

        return if (TransactionType.DEBIT == transaction.type) {

            //TODO ISSO AQUI PODE ESTAR DENTRO DO FACTORY E DO DOMAIN DE TRANSACTION OU AQUI MESMO?
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

            accountPersistence.create(account)
            transactionPersistence.create(newTransaction)

        } else {
            val account = transaction.accountDeposit()
            val newTransaction = transaction.copy(account = account)

            accountPersistence.create(account)
            transactionPersistence.create(newTransaction)
        }
    }

    override suspend fun getTransactions(
        accountId: UUID,
        startDate: LocalDate,
        finishDate: LocalDate
    ): List<Transaction> {
        val account = accountPersistence.get(accountId).orElseThrow {
            throw ResourceNotFoundException(message = "Account not found.")
        }
        return transactionPersistence.getTransactions(startDate, finishDate, account)
    }

    private suspend fun getDailyDebitTransactionSum(date: LocalDate, account: Account, type: TransactionType) : Int {
        return transactionPersistence.sumDailyDebitTransactionsAmountByAccount(date, account, type)
    }
}