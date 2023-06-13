package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.port.adapter.AccountUseCase
import com.dock.bank.digitalaccount.infra.rest.converter.toCreateResponse
import com.dock.bank.digitalaccount.infra.rest.converter.toGetResponse
import com.dock.bank.digitalaccount.infra.rest.dto.CreateAccountResponse
import com.dock.bank.digitalaccount.infra.rest.dto.GetAccountResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountUseCase: AccountUseCase,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(AccountController::class.java)
    }

    @PostMapping
    suspend fun create(
        @RequestHeader("holder-cpf") holderCpf: String,
    ) : CreateAccountResponse {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("holder_cpf", holderCpf)
        logger.info("Initializing POST request to /accounts endpoint with holder cpf: ${holderCpf}.")
        val response = accountUseCase.create(holderCpf).toCreateResponse()
        MDC.clear()
        return response
    }

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: UUID) : GetAccountResponse {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("account_id", id.toString())
        val response = accountUseCase.get(id).toGetResponse()
        logger.info("Initializing GET request to /accounts/{id} endpoint with account id: $id.")
        return response
    }

    @PatchMapping("/{id}/enable")
    suspend fun enable(@PathVariable id: UUID) : ResponseEntity<Unit> {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("account_id", id.toString())
        val response = accountUseCase.enable(id, Status.ACTIVATED)
        logger.info("Initializing PATCH request to /accounts/enable endpoint with account id: $id.")
        if (response) {
            return ResponseEntity.ok(Unit)
        }
        return ResponseEntity.notFound().build()
    }

    @PatchMapping("/{id}/disable")
    suspend fun disable(@PathVariable id: UUID) : ResponseEntity<Unit> {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("account_id", id.toString())
        val response = accountUseCase.enable(id, Status.DISABLED)
        logger.info("Initializing PATCH request to /accounts/disable endpoint with account id: $id.")
        if (response) {
            return ResponseEntity.ok(Unit)
        }
        return ResponseEntity.notFound().build()
    }

    @PatchMapping("/{id}/block")
    suspend fun block(@PathVariable id: UUID) : ResponseEntity<Unit> {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("account_id", id.toString())
        val response = accountUseCase.enable(id, Status.BLOCKED)
        logger.info("Initializing PATCH request to /accounts/block endpoint with account id: $id.")
        if (response) {
            return ResponseEntity.ok(Unit)
        }
        return ResponseEntity.notFound().build()
    }
}