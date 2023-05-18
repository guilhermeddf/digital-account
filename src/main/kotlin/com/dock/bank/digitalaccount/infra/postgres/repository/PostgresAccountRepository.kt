package com.dock.bank.digitalaccount.infra.postgres.repository

import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.infra.postgres.model.AccountTable
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface PostgresAccountRepository : JpaRepository<AccountTable, UUID> {

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