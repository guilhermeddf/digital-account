package com.dock.bank.digitalaccount.core.usecase

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Credentials
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.port.adapter.AccountGenerator
import com.dock.bank.digitalaccount.core.port.adapter.AccountUseCase
import com.dock.bank.digitalaccount.core.port.persistence.AccountPersistence
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence

import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.util.UUID

class AccountUseCaseImpl(
    private val accountPersistence: AccountPersistence,
    private val holderPersistence: HolderPersistence,
    private val accountGenerator: AccountGenerator,
) : AccountUseCase {

    companion object {
        private val logger = LoggerFactory.getLogger(AccountUseCaseImpl::class.java)
    }

    override suspend fun create(holderCpf: String): Mono<Account> {
        return retrieveHolder(holderCpf).map { accountGenerator.generateAccount(it) }
    }

    override suspend fun disable(id: UUID, status: Status) : Boolean {
        return accountPersistence.disable(id, status)
    }

    override suspend fun block(id: UUID, status: Status): Boolean {
        return accountPersistence.block(id, status)
    }

    override suspend fun enable(id: UUID, status: Status): Boolean {
        return accountPersistence.enable(id, status)
    }

    override suspend fun get(id: UUID, credentials: Credentials): Mono<Account> {
        return accountPersistence.get(id).switchIfEmpty(throw ResourceNotFoundException(message = "Account not found."))
    }

    private suspend fun retrieveHolder(holderCpf: String) : Mono<Holder> {
        return holderPersistence.findByCpf(holderCpf)
    }
}