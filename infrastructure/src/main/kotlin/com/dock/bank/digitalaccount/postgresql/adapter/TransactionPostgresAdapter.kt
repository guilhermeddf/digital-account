package com.dock.bank.digitalaccount.postgresql.adapter

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.ports.spi.TransactionDatabasePort
import com.dock.bank.digitalaccount.postgresql.mapper.toEntity
import com.dock.bank.digitalaccount.postgresql.mapper.toTable
import com.dock.bank.digitalaccount.postgresql.repository.PostgresTransactionRepository
import com.dock.bank.digitalaccount.utils.DateUtils
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Component
@Primary
class TransactionPostgresAdapter(
    private val postgresTransactionRepository: PostgresTransactionRepository
) : TransactionDatabasePort {

    companion object {
        private val logger = LoggerFactory.getLogger(TransactionPostgresAdapter::class.java)
    }

    override suspend fun create(transaction: Transaction) : Transaction {
         logger.info("Creating transaction with id ${transaction.id}")
         return postgresTransactionRepository.save(transaction.toTable()).toEntity()
    }

    override suspend fun getTransactions(
        startDate: LocalDate,
        finishDate: LocalDate,
        account: Account
    ): List<Transaction> {
        logger.info("Getting transactions with startDate: $startDate, finishDate: $finishDate and account id: ${account.id}")
        val response = postgresTransactionRepository.getTransactionsByStartDateAndFinishDateAndAccount(
            startDate = DateUtils.getStartDate(startDate),
            finishDate = DateUtils.getFinishDate(finishDate),
            account = account.toTable(),
        )

        return response.map { transactionTable -> transactionTable.toEntity() }
    }

    override suspend fun sumDailyDebitTransactionsAmountByAccount(
        date: LocalDate,
        account: Account,
        type: TransactionType
    ) : Int {
        logger.info("Getting sum of daily debit transaction for account with id: ${account.id}")
        return postgresTransactionRepository.sumDebitTransactionsAmountByCreatedDateAndAccount(
            createdInitDate = DateUtils.getStartDate(date),
            createdFinishDate = DateUtils.getFinishDate(date),
            account = account.toTable(),
            type = type
        ) ?: 0
    }
}