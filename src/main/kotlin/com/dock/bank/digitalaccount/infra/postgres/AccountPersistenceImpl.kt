package com.dock.bank.digitalaccount.infra.postgres

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.port.persistence.AccountPersistence
import com.dock.bank.digitalaccount.infra.postgres.converter.toEntity
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresAccountRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.Optional
@Repository
class AccountPersistenceImpl(
    private val postgresAccountRepository: PostgresAccountRepository
) : AccountPersistence {

    companion object {
        private val logger = LoggerFactory.getLogger(AccountPersistenceImpl::class.java)
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