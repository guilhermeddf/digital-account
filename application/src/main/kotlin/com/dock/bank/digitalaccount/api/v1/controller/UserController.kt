package com.dock.bank.digitalaccount.api.v1.controller

import com.dock.bank.digitalaccount.api.v1.mapper.toCreateUserResponse
import com.dock.bank.digitalaccount.api.v1.mapper.toEntity
import com.dock.bank.digitalaccount.api.v1.request.CreateUserRequest
import com.dock.bank.digitalaccount.api.v1.response.CreateUserResponse
import com.dock.bank.digitalaccount.ports.api.UserServicePort
import com.dock.bank.digitalaccount.utils.mask
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userServicePort: UserServicePort
) {
    companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }

    @PostMapping
    fun create(@RequestBody userRequest: CreateUserRequest): CreateUserResponse {
        val requestId = UUID.randomUUID()
        val user = userRequest.toEntity()
        MDC.put("request_id", requestId.toString())
        MDC.put("user_id", user.id.toString())
        MDC.put("username", userRequest.username)
        MDC.put("password", userRequest.password.mask())

        logger.info("Initializing POST request to /users endpoint with account username: ${userRequest.username}.")
        val response = userServicePort.create(user)
        logger.info("User created with id ${response.id}.")
        MDC.clear()
        return response.toCreateUserResponse()
    }
}