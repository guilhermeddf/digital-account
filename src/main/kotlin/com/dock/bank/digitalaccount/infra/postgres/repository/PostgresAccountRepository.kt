package com.dock.bank.digitalaccount.infra.postgres.repository

import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.infra.postgres.model.AccountTable
import jakarta.transaction.Transactional
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostgresAccountRepository : ReactiveCrudRepository<AccountTable, UUID> {

    @Modifying
    @Transactional
    @Query(value = "update AccountTable at set at.status = :status where at.id = :id")
    fun disable(@Param("id") id: UUID, @Param("status") status: Status) : Int

    @Modifying
    @Transactional
    @Query(value = "update AccountTable at set at.status = :status where at.id = :id")
    fun block(@Param("id") id: UUID, @Param("status") status: Status) : Int

    @Modifying
    @Transactional
    @Query(value = "update AccountTable at set at.status = :status where at.id = :id")
    fun enable(@Param("id") id: UUID, @Param("status") status: Status): Int
}