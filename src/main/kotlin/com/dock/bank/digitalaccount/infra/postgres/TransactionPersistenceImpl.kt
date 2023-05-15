package com.dock.bank.digitalaccount.infra.postgres

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.core.port.persistence.TransactionPersistence
import com.dock.bank.digitalaccount.infra.postgres.converter.toEntity
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresTransactionRepository
import com.dock.bank.digitalaccount.utils.DateUtils
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class TransactionPersistenceImpl(
    private val postgresTransactionRepository: PostgresTransactionRepository
) : TransactionPersistence {
    override suspend fun create(transaction: Transaction) : Transaction {
         return postgresTransactionRepository.save(transaction.toTable()).toEntity()
    }

    override suspend fun getTransactions(
        startDate: LocalDate,
        finishDate: LocalDate,
        account: Account
    ): List<Transaction> {

        return postgresTransactionRepository.getTransactionsByStartDateAndFinishDateAndAccount(
            startDate = DateUtils.getStartDate(startDate),
            finishDate = DateUtils.getFinishDate(finishDate),
            account = account.toTable(),
        ).map { transactionTable -> transactionTable.toEntity() }
    }

    override suspend fun sumDailyDebitTransactionsAmountByAccount(
        date: LocalDate,
        account: Account,
        type: TransactionType
    ) : Int {
        return postgresTransactionRepository.sumDebitTransactionsAmountByCreatedDateAndAccount(
            createdInitDate = DateUtils.getStartDate(date),
            createdFinishDate = DateUtils.getFinishDate(date),
            account = account.toTable(),
            type = type
        ) ?: 0
    }
}