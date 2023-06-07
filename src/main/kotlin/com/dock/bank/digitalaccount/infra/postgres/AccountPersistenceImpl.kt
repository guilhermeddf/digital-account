package com.dock.bank.digitalaccount.infra.postgres

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.port.persistence.AccountPersistence
import com.dock.bank.digitalaccount.infra.postgres.converter.toEntity
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresAccountRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.UUID
import java.util.Optional
@Repository
class AccountPersistenceImpl(
    private val postgresAccountRepository: PostgresAccountRepository
) : AccountPersistence {

    override suspend fun create(account: Account): Mono<Account> {
        return postgresAccountRepository.save(account.toTable()).map { account -> account.toEntity() }
    }

    override suspend fun disable(id: UUID, status: Status) : Boolean {
        val result = postgresAccountRepository.disable(id, status)
        return result != 0
    }

    override suspend fun block(id: UUID, status: Status): Boolean {
        val result = postgresAccountRepository.block(id, status)
        return result != 0
    }

    override suspend fun enable(id: UUID, status: Status): Boolean {
        val result = postgresAccountRepository.enable(id, status)
        return result != 0
    }

    override suspend fun get(id: UUID): Mono<Account> {
        return postgresAccountRepository.findById(id)
            .map { account -> account.toEntity() }
            .switchIfEmpty(Mono.empty())
    }
}