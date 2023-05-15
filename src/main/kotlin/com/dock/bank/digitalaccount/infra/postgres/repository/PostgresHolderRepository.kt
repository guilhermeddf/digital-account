package com.dock.bank.digitalaccount.infra.postgres.repository

import com.dock.bank.digitalaccount.infra.postgres.model.HolderTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional
import java.util.UUID

interface PostgresHolderRepository : JpaRepository<HolderTable, UUID> {

    @Query(value = "from HolderTable t where :cpf = t.cpf")
    fun findHolderByCpf(@Param("cpf") cpf: String) : Optional<HolderTable>
}