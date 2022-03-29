package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.core.port.adapter.HolderUseCase
import com.dock.bank.digitalaccount.infra.rest.converter.toCreateResponse
import com.dock.bank.digitalaccount.infra.rest.converter.toEntity
import com.dock.bank.digitalaccount.infra.rest.dto.CreateHolderRequest
import com.dock.bank.digitalaccount.infra.rest.dto.CreateHolderResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/holders")
class HolderController(
    private val holderUseCase: HolderUseCase
) {
    @PostMapping
    suspend fun create(@RequestBody createHolderRequest: CreateHolderRequest) : CreateHolderResponse {

        return holderUseCase.create(createHolderRequest.toEntity()).toCreateResponse()
    }

    //Aqui eu poderia fazer uso de uma deleção logica criando um campo isDeleted por exemplo, setando como true,
    //e colocando a conta do portador como desativada, mas resolvi fazer a deleção fisica pra mostrar que sei usar
    //delete on cascade no posrgres XD.
    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: UUID) : ResponseEntity<Unit> {
        holderUseCase.delete(id)
        return ResponseEntity.ok(Unit)
    }
}