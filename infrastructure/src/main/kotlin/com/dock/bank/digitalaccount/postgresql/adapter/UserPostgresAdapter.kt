package com.dock.bank.digitalaccount.postgresql.adapter

import com.dock.bank.digitalaccount.core.domain.User
import com.dock.bank.digitalaccount.postgresql.mapper.toEntity
import com.dock.bank.digitalaccount.postgresql.mapper.toTable
import com.dock.bank.digitalaccount.postgresql.repository.PostgresUserRepository
import com.dock.bank.digitalaccount.ports.spi.UserDatabasePort
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class UserPostgresAdapter(
    private val userRepository: PostgresUserRepository
): UserDatabasePort {

    companion object {
        private val logger = LoggerFactory.getLogger(UserPostgresAdapter::class.java)
    }
    override fun create(user: User): User {
        logger.info("Encrypting password from user with id ${user.id} and username: ${user.username}")
        //val enc = BCryptPasswordEncoder()
        //val password = enc.encode(user.password)

        logger.info("Creating user with id: ${user.id} and username ${user.username}")
        return userRepository.save(user.toTable()).toEntity()
    }
}