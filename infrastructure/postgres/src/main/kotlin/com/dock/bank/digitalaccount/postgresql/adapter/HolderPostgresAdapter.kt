package com.dock.bank.digitalaccount.postgresql.adapter

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exception.GeneralException
import com.dock.bank.digitalaccount.postgresql.mapper.toEntity
import com.dock.bank.digitalaccount.postgresql.mapper.toTable
import com.dock.bank.digitalaccount.postgresql.repository.PostgresHolderRepository
import com.dock.bank.digitalaccount.ports.spi.HolderDatabasePort
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.*

@Component
@Primary
class HolderPostgresAdapter (
    private val postgresHolderRepository: PostgresHolderRepository,
) : HolderDatabasePort {

    companion object {
        private val logger = LoggerFactory.getLogger(HolderPostgresAdapter::class.java)
    }

    override suspend fun create(holder: Holder): Optional<Holder> {
        try {
            logger.info("Searching holder with cpf ${holder.cpf} on postgres database.")
            val postgresStoredHolder = postgresHolderRepository.findHolderByCpf(holder.cpf)
            return if (postgresStoredHolder.isEmpty) {
                logger.info("Creating holder with id ${holder.id} on postgres database.")
                Optional.of(postgresHolderRepository.save(holder.toTable()).toEntity())
            } else {
                logger.error("Holder with id: ${holder.id} and username: ${holder.name} its already on postgres database.")
                Optional.empty<Holder>()
            }
        } catch (e: Exception) {
            throw GeneralException("Error creating holder with id ${holder.id} on postgres database.")
        }
    }

    override suspend fun findByCpf(holderCpf: String): Optional<Holder> {
       try {
           logger.info("Searching holder with cpf $holderCpf on postgres database.")
           val postgresStoredHolder = postgresHolderRepository.findHolderByCpf(holderCpf)
           return if (postgresStoredHolder.isEmpty) {
               Optional.empty<Holder>()
           } else {
               Optional.of(postgresStoredHolder.get().toEntity())
           }
       } catch (e: Exception) {
           throw GeneralException("Error getting holder with cpf $holderCpf on postgres database.")
       }
    }

    override suspend fun delete(id: UUID) {
        try {
            logger.info("Deleting holder with id: $id")
            if(postgresHolderRepository.existsById(id)) {
                return postgresHolderRepository.deleteById(id)
            }
        } catch (e: Exception) {
            throw GeneralException("Error creating holder with id $id on postgres database.")
        }
    }

    override suspend fun getAll(): List<Holder> {
        try {
            logger.info("Getting all holders on postgres database.")
            return postgresHolderRepository.findAll().map { it.toEntity() }
        } catch (e: Exception) {
            throw GeneralException("Error getting all holders on postgres database.")
        }
    }
}