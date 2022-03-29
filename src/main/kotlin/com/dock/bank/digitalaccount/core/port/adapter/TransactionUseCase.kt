package com.dock.bank.digitalaccount.core.port.adapter

import com.dock.bank.digitalaccount.core.domain.Transaction
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

interface TransactionUseCase {
    suspend fun create(transaction: Transaction) : Transaction
    suspend fun getTransactions(accountId: UUID, startDate: LocalDate, finishDate: LocalDate) : List<Transaction>
}