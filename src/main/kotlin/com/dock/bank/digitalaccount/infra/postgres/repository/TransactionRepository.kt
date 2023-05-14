package com.dock.bank.digitalaccount.infra.postgres.repository

import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.infra.postgres.model.AccountTable
import com.dock.bank.digitalaccount.infra.postgres.model.TransactionTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.OffsetDateTime
import java.util.UUID

interface TransactionRepository : JpaRepository<TransactionTable, UUID> {

    @Query(value = "SELECT SUM(tt.amount) FROM TransactionTable tt WHERE tt.createdDate BETWEEN :createdInitDate AND :createdFinishDate AND tt.type = :type AND tt.account = :account")
    fun sumDebitTransactionsAmountByCreatedDateAndAccount(
        @Param("createdInitDate") createdInitDate: OffsetDateTime,
        @Param("createdFinishDate") createdFinishDate: OffsetDateTime,
        @Param("account") account: AccountTable,
        @Param("type") type: TransactionType
    ) : Int?

    @Query(value = "FROM TransactionTable tt WHERE tt.createdDate BETWEEN :startDate AND :finishDate AND tt.account = :account")
    fun getTransactionsByStartDateAndFinishDateAndAccount(
        @Param("startDate") startDate: OffsetDateTime,
        @Param("finishDate") finishDate: OffsetDateTime,
        @Param("account") account: AccountTable,
    ) : List<TransactionTable>
}