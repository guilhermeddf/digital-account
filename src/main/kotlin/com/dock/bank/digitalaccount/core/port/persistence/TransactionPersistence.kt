package com.dock.bank.digitalaccount.core.port.persistence

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import java.time.LocalDate
import java.time.OffsetDateTime

interface TransactionPersistence {
    suspend fun create(transaction: Transaction) : Transaction
    suspend fun getTransactions(startDate: LocalDate, finishDate: LocalDate, account: Account) : List<Transaction>
    suspend fun sumDailyDebitTransactionsAmountByAccount(date: LocalDate, account: Account, type: TransactionType) : Int
}