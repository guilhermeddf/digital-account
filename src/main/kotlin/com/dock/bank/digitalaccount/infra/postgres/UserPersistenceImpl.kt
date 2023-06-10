package com.dock.bank.digitalaccount.infra.postgres

import com.dock.bank.digitalaccount.core.domain.User
import com.dock.bank.digitalaccount.core.port.persistence.UserPersistence
import com.dock.bank.digitalaccount.infra.postgres.converter.toEntity
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresUserRepository
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceImpl(
    private val userRepository: PostgresUserRepository
): UserPersistence {

    override fun create(user: User): User {
        return userRepository.save(user.toTable()).toEntity()
    }
}