package com.dock.bank.digitalaccount.postgresql.adapter

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.postgresql.mapper.toEntity
import com.dock.bank.digitalaccount.postgresql.mapper.toTable
import com.dock.bank.digitalaccount.ports.spi.AccountDatabasePort
import com.dock.bank.digitalaccount.postgresql.repository.PostgresAccountRepository
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.Optional
@Component
@Primary
class AccountPostgresAdapter(
    private val postgresAccountRepository: PostgresAccountRepository
) : AccountDatabasePort {

    companion object {
        private val logger = LoggerFactory.getLogger(AccountPostgresAdapter::class.java)
    }

    override suspend fun create(account: Account): Account {
        logger.info("Creating account with id ${account.id}.")
        return postgresAccountRepository.save(account.toTable()).toEntity()
    }

    override suspend fun disable(id: UUID, status: Status) : Boolean {
        logger.info("Disabling account with id $id changing status to: ${status.name}.")
        val result = postgresAccountRepository.disable(id, status)
        return result != 0
    }

    override suspend fun block(id: UUID, status: Status): Boolean {
        logger.info("Blocking account with id $id changing status to: ${status.name}.")
        val result = postgresAccountRepository.block(id, status)
        return result != 0
    }

    override suspend fun enable(id: UUID, status: Status): Boolean {
        logger.info("Enabling account with id $id changing status to: ${status.name}.")
        val result = postgresAccountRepository.enable(id, status)
        return result != 0
    }

    override suspend fun get(id: UUID): Optional<Account> {
        logger.info("Getting account with id ${id}.")
        val account = postgresAccountRepository.findById(id)
        return if (account.isEmpty) {
            Optional.empty<Account>()
        } else {
            Optional.of(account.get().toEntity())
        }
    }
}