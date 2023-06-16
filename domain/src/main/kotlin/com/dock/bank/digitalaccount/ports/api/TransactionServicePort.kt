package com.dock.bank.digitalaccount.ports.api

import com.dock.bank.digitalaccount.core.domain.Transaction
import java.time.LocalDate
import java.util.UUID

interface TransactionServicePort {
    suspend fun create(transaction: Transaction) : Transaction
    suspend fun getTransactions(accountId: UUID, startDate: LocalDate, finishDate: LocalDate) : List<Transaction>
}