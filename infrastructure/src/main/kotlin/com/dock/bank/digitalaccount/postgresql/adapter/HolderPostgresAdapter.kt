package com.dock.bank.digitalaccount.postgresql.adapter

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.postgresql.mapper.toEntity
import com.dock.bank.digitalaccount.postgresql.mapper.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresHolderRepository
import com.dock.bank.digitalaccount.ports.spi.HolderDatabasePort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class HolderPostgresAdapter (
    private val postgresHolderRepository: PostgresHolderRepository,
) : HolderDatabasePort {

    companion object {
        private val logger = LoggerFactory.getLogger(HolderPostgresAdapter::class.java)
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