package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.core.domain.Login
import com.dock.bank.digitalaccount.core.usecase.AccountUseCaseImpl
import com.dock.bank.digitalaccount.infra.postgres.model.Role
import com.dock.bank.digitalaccount.infra.postgres.model.UserTable
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresUserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class RegisterController(
    private val repository: PostgresUserRepository
) {
    companion object {
        private val logger = LoggerFactory.getLogger(RegisterController::class.java)
    }
    @PostMapping("/register")
    fun register(@RequestBody login: Login) {
        val user = UserTable(UUID.randomUUID(), login.username, login.password, Role.ADMIN)
        val enc = BCryptPasswordEncoder()
        val password = enc.encode(user.password)
        repository.save(user.copy(password = password))
        logger.info(user.toString())
    }
}