package com.dock.bank.digitalaccount.infra.database

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.port.persistence.AccountPersistence
import com.dock.bank.digitalaccount.infra.database.converter.toEntity
import com.dock.bank.digitalaccount.infra.database.converter.toTable
import com.dock.bank.digitalaccount.infra.database.repository.AccountRepository
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.Optional
@Repository
class AccountPersistenceImpl(
    private val accountRepository: AccountRepository
) : AccountPersistence {

    override suspend fun create(account: Account): Account {
        return accountRepository.save(account.toTable()).toEntity()
    }

    override suspend fun disable(id: UUID, status: Status) : Boolean {
        val result = accountRepository.disable(id, status)
        return result != 0
    }

    override suspend fun block(id: UUID, status: Status): Boolean {
        val result = accountRepository.block(id, status)
        return result != 0
    }

    override suspend fun enable(id: UUID, status: Status): Boolean {
        val result = accountRepository.enable(id, status)
        return result != 0
    }

    override suspend fun get(id: UUID): Optional<Account> {
        val account = accountRepository.findById(id)
        return if (account.isEmpty) {
            Optional.empty<Account>()
        } else {
            Optional.of(account.get().toEntity())
        }
    }
}