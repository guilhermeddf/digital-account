package com.dock.bank.digitalaccount.infra.postgres

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import com.dock.bank.digitalaccount.infra.dynamodb.DynamoHolderRepository
import com.dock.bank.digitalaccount.infra.postgres.converter.toEntity
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresHolderRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.UUID
import java.util.Optional

@Repository
class HolderPersistenceImpl (
    private val postgresHolderRepository: PostgresHolderRepository
) : HolderPersistence {

    override suspend fun create(holder: Holder): Mono<Holder> {
        return postgresHolderRepository.findHolderByCpf(holder.cpf)
            .switchIfEmpty(
                postgresHolderRepository.save(holder.toTable())
            ).map { holder -> holder.toEntity() }
    }

    override suspend fun findByCpf(holderCpf: String): Mono<Holder> {
        return postgresHolderRepository.findHolderByCpf(holderCpf)
            .map { holder -> holder.toEntity() }
            .switchIfEmpty(Mono.empty())
    }

    override suspend fun delete(id: UUID): Mono<Void>  {
        return postgresHolderRepository.deleteById(id)
    }
}