package com.dock.bank.digitalaccount.infra.postgres

import com.dock.bank.digitalaccount.core.domain.User
import com.dock.bank.digitalaccount.core.port.persistence.UserPersistence
import com.dock.bank.digitalaccount.infra.postgres.converter.toEntity
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresUserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceImpl(
    private val userRepository: PostgresUserRepository
): UserPersistence {

    companion object {
        private val logger = LoggerFactory.getLogger(UserPersistenceImpl::class.java)
    }
    override fun create(user: User): User {
        logger.info("Creating user with id: ${user.id} and username ${user.username}")
        return userRepository.save(user.toTable()).toEntity()
    }
}