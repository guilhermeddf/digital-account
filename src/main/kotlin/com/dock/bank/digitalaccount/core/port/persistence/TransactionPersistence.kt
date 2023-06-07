package com.dock.bank.digitalaccount.core.port.persistence

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.OffsetDateTime

interface TransactionPersistence {
    suspend fun create(transaction: Transaction) : Mono<Transaction>
    suspend fun getTransactions(startDate: LocalDate, finishDate: LocalDate, account: Account) : Flux<Transaction>
    suspend fun sumDailyDebitTransactionsAmountByAccount(date: LocalDate, account: Account, type: TransactionType) : Mono<Int>
}