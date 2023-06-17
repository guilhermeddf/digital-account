package com.dock.bank.digitalaccount.infra.postgres.repository

import com.dock.bank.digitalaccount.postgresql.model.UserTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostgresUserRepository: JpaRepository<UserTable, UUID> {

    @Query(value = "from UserTable t where :username = t.username")
    fun findByUsername(@Param("username") username: String): Optional<UserTable>
}