package com.dock.bank.digitalaccount.ports.spi.database

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import java.time.LocalDate

interface TransactionDatabasePort {
    suspend fun create(transaction: Transaction) : Transaction
    suspend fun getTransactions(startDate: LocalDate, finishDate: LocalDate, account: Account) : List<Transaction>
    suspend fun sumDailyDebitTransactionsAmountByAccount(date: LocalDate, account: Account, type: TransactionType) : Int
}