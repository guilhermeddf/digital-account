package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.ports.api.AccountGeneratorServicePort
import com.dock.bank.digitalaccount.ports.api.AccountServicePort
import com.dock.bank.digitalaccount.ports.spi.AccountDatabasePort
import com.dock.bank.digitalaccount.ports.spi.HolderDatabasePort
import org.slf4j.LoggerFactory
import java.util.*

class AccountServicePortImpl(
    private val accountPersistence: AccountDatabasePort,
    private val holderPersistence: HolderDatabasePort,
    private val accountGenerator: AccountGeneratorServicePort,
) : AccountServicePort {

    companion object {
        private val logger = LoggerFactory.getLogger(AccountServicePortImpl::class.java)
    }

    override suspend fun create(holderCpf: String): Account {
        val storedHolder = retrieveHolder(holderCpf)
        logger.info("Holder with id ${storedHolder.id} was successfully retrieved.")
        return accountPersistence.create(accountGenerator.generateAccount(storedHolder))
    }

    override suspend fun disable(id: UUID, status: Status) = accountPersistence.disable(id, status)
    override suspend fun block(id: UUID, status: Status) = accountPersistence.block(id, status)
    override suspend fun enable(id: UUID, status: Status) = accountPersistence.enable(id, status)

    override suspend fun get(id: UUID): Account = accountPersistence.get(id).orElseThrow {
            throw ResourceNotFoundException(message = "Account not found.")
        }

    private suspend fun retrieveHolder(holderCpf: String) = holderPersistence.findByCpf(holderCpf).orElseThrow {
            throw ResourceNotFoundException(message = "Holder not Found.")
        }
}