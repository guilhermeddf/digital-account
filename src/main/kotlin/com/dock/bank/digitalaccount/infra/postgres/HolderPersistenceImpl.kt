package com.dock.bank.digitalaccount.infra.postgres

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import com.dock.bank.digitalaccount.infra.postgres.converter.toEntity
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresHolderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class HolderPersistenceImpl (
    private val postgresHolderRepository: PostgresHolderRepository,
) : HolderPersistence {

    companion object {
        private val logger = LoggerFactory.getLogger(HolderPersistenceImpl::class.java)
    }

    override suspend fun create(holder: Holder): Optional<Holder> {
        logger.info("Searching holder with cpf ${holder.cpf} on database.")
        val postgresStoredHolder = postgresHolderRepository.findHolderByCpf(holder.cpf)
        return if (postgresStoredHolder.isEmpty) {
            logger.info("Creating holder with id ${holder.id} on database.")
            Optional.of(postgresHolderRepository.save(holder.toTable()).toEntity())
        } else {
            logger.error("Holder with id: ${holder.id} and username: ${holder.name} its already on database.")
            Optional.empty<Holder>()
        }
    }

    override suspend fun findByCpf(holderCpf: String): Optional<Holder> {
        logger.info("Searching holder with cpf $holderCpf on database.")
        val postgresStoredHolder = postgresHolderRepository.findHolderByCpf(holderCpf)
        return if (postgresStoredHolder.isEmpty) {
            Optional.empty<Holder>()
        } else {
            Optional.of(postgresStoredHolder.get().toEntity())
        }
    }

    override suspend fun delete(id: UUID) {
        logger.info("Deleting holder with id: $id")
        if(postgresHolderRepository.existsById(id)) {
            return postgresHolderRepository.deleteById(id)
        }
        throw ResourceNotFoundException("Holder not found.")
    }

    override suspend fun getAll(): List<Holder> {
        logger.info("Getting all holders on database.")
        return postgresHolderRepository.findAll().map { it.toEntity() }
    }
}