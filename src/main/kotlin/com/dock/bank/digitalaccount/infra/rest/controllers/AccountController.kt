package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.port.adapter.AccountUseCase
import com.dock.bank.digitalaccount.infra.rest.converter.toCreateResponse
import com.dock.bank.digitalaccount.infra.rest.converter.toGetResponse
import com.dock.bank.digitalaccount.infra.rest.dto.CreateAccountResponse
import com.dock.bank.digitalaccount.infra.rest.dto.GetAccountResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountUseCase: AccountUseCase,
) {
    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: UUID) : GetAccountResponse {
        return accountUseCase.get(id).toGetResponse()
    }

    @PostMapping
    suspend fun create(
        @RequestHeader("holder-cpf") holderCpf: String,
    ) : CreateAccountResponse {
        return accountUseCase.create(holderCpf).toCreateResponse()
    }

    @PatchMapping("/{id}/enable")
    suspend fun enable(@PathVariable id: UUID) : ResponseEntity<Unit> {
        if (accountUseCase.enable(id, Status.ACTIVATED)) {
            return ResponseEntity.ok(Unit)
        }
        return ResponseEntity.notFound().build()
    }

    @PatchMapping("/{id}/disable")
    suspend fun disable(@PathVariable id: UUID) : ResponseEntity<Unit> {
        if (accountUseCase.disable(id, Status.DISABLED)) {
            return ResponseEntity.ok(Unit)
        }
        return ResponseEntity.notFound().build()
    }

    @PatchMapping("/{id}/block")
    suspend fun block(@PathVariable id: UUID) : ResponseEntity<Unit> {
        if (accountUseCase.block(id, Status.BLOCKED)) {
            return ResponseEntity.ok(Unit)
        }
        return ResponseEntity.notFound().build()
    }
}