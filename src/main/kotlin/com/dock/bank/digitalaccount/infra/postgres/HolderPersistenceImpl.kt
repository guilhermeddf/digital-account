package com.dock.bank.digitalaccount.infra.postgres

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import com.dock.bank.digitalaccount.infra.dynamodb.DynamoHolderRepository
import com.dock.bank.digitalaccount.infra.postgres.converter.toEntity
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresHolderRepository
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.Optional

@Repository
class HolderPersistenceImpl (
    private val postgresHolderRepository: PostgresHolderRepository,
    private val dynamoHolderRepository: DynamoHolderRepository
) : HolderPersistence {

    override suspend fun create(holder: Holder): Optional<Holder> {
        //val postgresStoredHolder = postgresHolderRepository.findHolderByCpf(holder.cpf)
        val dynamoStoredHolder = dynamoHolderRepository.findHolderByCpf(holder.cpf)
        return if (dynamoStoredHolder.isEmpty) {
            Optional.of(dynamoHolderRepository.save(holder.toTable()).toEntity())
        } else {
            Optional.empty<Holder>()
        }
    }

    override suspend fun findByCpf(holderCpf: String): Optional<Holder> {
        //val postgresStoredHolder = postgresHolderRepository.findHolderByCpf(holderCpf)
        val dynamoStoredHolder = dynamoHolderRepository.findHolderByCpf(holderCpf)
        return if (dynamoStoredHolder.isEmpty) {
            Optional.empty<Holder>()
        } else {
            Optional.of(dynamoStoredHolder.get().toEntity())
        }
    }

    override suspend fun delete(id: UUID)  {
        //if(postgresHolderRepository.existsById(id)) {
        //    return postgresHolderRepository.deleteById(id)
        //}
        if (dynamoHolderRepository.existsById(id)) {
            return dynamoHolderRepository.deleteById(id)
        }
        throw ResourceNotFoundException("Holder not found.")
    }
}