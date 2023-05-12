package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.port.adapter.AccountUseCase
import com.dock.bank.digitalaccount.infra.rest.converter.toCreateResponse
import com.dock.bank.digitalaccount.infra.rest.converter.toGetResponse
import com.dock.bank.digitalaccount.infra.rest.dto.CreateAccountResponse
import com.dock.bank.digitalaccount.infra.rest.dto.GetAccountResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountUseCase: AccountUseCase
) {

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: UUID) : GetAccountResponse {
        return accountUseCase.get(id).toGetResponse()
    }

    @PostMapping
    suspend fun create(
        @RequestHeader("holder-cpf") holderCpf: String,
        @RequestHeader("test") test: Boolean
    ) : CreateAccountResponse {
        return accountUseCase.create(holderCpf, test).toCreateResponse()
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