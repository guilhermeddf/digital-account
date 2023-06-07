package com.dock.bank.digitalaccount.infra.postgres.repository

import com.dock.bank.digitalaccount.infra.postgres.model.HolderTable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface PostgresHolderRepository : ReactiveCrudRepository<HolderTable, UUID> {

    @Query(value = "FROM HolderTable t where :cpf = t.cpf")
    fun findHolderByCpf(cpf: String) : Mono<HolderTable>
}