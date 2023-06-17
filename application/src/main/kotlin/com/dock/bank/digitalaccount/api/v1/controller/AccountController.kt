package com.dock.bank.digitalaccount.api.v1.controller

import com.dock.bank.digitalaccount.api.v1.mapper.toCreateResponse
import com.dock.bank.digitalaccount.api.v1.mapper.toGetResponse
import com.dock.bank.digitalaccount.api.v1.response.CreateAccountResponse
import com.dock.bank.digitalaccount.api.v1.response.GetAccountResponse
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.ports.api.AccountServicePort
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountServicePort: AccountServicePort,
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
        val response = accountServicePort.create(holderCpf).toCreateResponse()
        logger.info("Account created for holder with cpf ${response.cpf} and account id: ${response.id}")
        MDC.clear()
        return response
    }

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: UUID) : GetAccountResponse {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("account_id", id.toString())
        logger.info("Initializing GET request to /accounts/{id} endpoint with account id: $id.")
        val response = accountServicePort.get(id).toGetResponse()
        logger.info("Account returned with id: $id")
        MDC.clear()
        return response
    }

    @PatchMapping("/{id}/enable")
    suspend fun enable(@PathVariable id: UUID) : ResponseEntity<Unit> {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("account_id", id.toString())
        val response = accountServicePort.enable(id, Status.ACTIVATED)
        logger.info("Initializing PATCH request to /accounts/enable endpoint with account id: $id.")
        if (response) {
            logger.info("Account enabled with $id.")
            return ResponseEntity.ok(Unit)
        }
        MDC.clear()
        return ResponseEntity.notFound().build()
    }

    @PatchMapping("/{id}/disable")
    suspend fun disable(@PathVariable id: UUID) : ResponseEntity<Unit> {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("account_id", id.toString())
        val response = accountServicePort.enable(id, Status.DISABLED)
        logger.info("Initializing PATCH request to /accounts/disable endpoint with account id: $id.")
        if (response) {
            logger.info("Account disabled with $id.")
            return ResponseEntity.ok(Unit)
        }
        MDC.clear()
        return ResponseEntity.notFound().build()
    }

    @PatchMapping("/{id}/block")
    suspend fun block(@PathVariable id: UUID) : ResponseEntity<Unit> {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("account_id", id.toString())
        val response = accountServicePort.enable(id, Status.BLOCKED)
        logger.info("Initializing PATCH request to /accounts/block endpoint with account id: $id.")
        if (response) {
            logger.info("Account blocked with $id.")
            return ResponseEntity.ok(Unit)
        }
        MDC.clear()
        return ResponseEntity.notFound().build()
    }
}