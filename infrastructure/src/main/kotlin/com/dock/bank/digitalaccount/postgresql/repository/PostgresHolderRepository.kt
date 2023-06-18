package com.dock.bank.digitalaccount.postgresql.repository

import com.dock.bank.digitalaccount.postgresql.model.HolderTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface PostgresHolderRepository : JpaRepository<HolderTable, UUID> {

    @Query(value = "from HolderTable t where :cpf = t.cpf")
    fun findHolderByCpf(@Param("cpf") cpf: String) : Optional<HolderTable>
}