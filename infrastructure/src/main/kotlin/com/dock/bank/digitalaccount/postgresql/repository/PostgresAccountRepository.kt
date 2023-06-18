package com.dock.bank.digitalaccount.postgresql.repository

import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.postgresql.model.AccountTable
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
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