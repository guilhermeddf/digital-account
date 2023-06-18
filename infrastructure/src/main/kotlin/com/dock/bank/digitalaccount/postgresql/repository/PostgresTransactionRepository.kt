package com.dock.bank.digitalaccount.postgresql.repository

import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.postgresql.model.AccountTable
import com.dock.bank.digitalaccount.postgresql.model.TransactionTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.UUID

@Repository
interface PostgresTransactionRepository : JpaRepository<TransactionTable, UUID> {

    @Query(value = "SELECT SUM(tt.amount) FROM TransactionTable tt WHERE tt.createdDate BETWEEN :createdInitDate AND :createdFinishDate AND tt.type = :type AND tt.account = :account")
    fun sumDebitTransactionsAmountByCreatedDateAndAccount(
        @Param("createdInitDate") createdInitDate: OffsetDateTime,
        @Param("createdFinishDate") createdFinishDate: OffsetDateTime,
        @Param("account") account: AccountTable,
        @Param("type") type: TransactionType
    ) : Int?

    @Query(value = "FROM TransactionTable tt WHERE tt.createdDate >= :startDate AND tt.createdDate <= :finishDate AND tt.account = :account")
    fun getTransactionsByStartDateAndFinishDateAndAccount(
        @Param("startDate") startDate: OffsetDateTime,
        @Param("finishDate") finishDate: OffsetDateTime,
        @Param("account") account: AccountTable,
    ) : List<TransactionTable>
}