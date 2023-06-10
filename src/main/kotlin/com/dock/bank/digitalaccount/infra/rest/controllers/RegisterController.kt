package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.core.service.RegisterService
import com.dock.bank.digitalaccount.infra.rest.converter.toEntity
import com.dock.bank.digitalaccount.infra.rest.dto.CreateRegisterRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class RegisterController(
    private val registerService: RegisterService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(RegisterController::class.java)
    }
    @PostMapping("/register")
    fun register(@RequestBody register: CreateRegisterRequest) {
        val user = register.toEntity()
        registerService.register(user)

    }
}