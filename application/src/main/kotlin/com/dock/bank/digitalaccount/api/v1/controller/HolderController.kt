package com.dock.bank.digitalaccount.api.v1.controller

import com.dock.bank.digitalaccount.api.v1.mapper.toCreateResponse
import com.dock.bank.digitalaccount.api.v1.mapper.toEntity
import com.dock.bank.digitalaccount.api.v1.request.CreateHolderRequest
import com.dock.bank.digitalaccount.api.v1.response.CreateHolderResponse
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.ports.api.HolderServicePort
import com.dock.bank.digitalaccount.utils.mask
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/holders")
class HolderController(
    private val holderServicePort: HolderServicePort,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(HolderController::class.java)
    }
    @PostMapping
    suspend fun create(@RequestBody createHolderRequest: CreateHolderRequest) : CreateHolderResponse {
        val holder = createHolderRequest.toEntity()
        val requestId = UUID.randomUUID().toString()
        MDC.put("request_id", requestId)
        MDC.put("holder_id", holder.id.toString())
        MDC.put("cpf", holder.cpf.mask())
        MDC.put("holder_name", holder.name)

        logger.info("Initializing POST request to /holders endpoint with id: ${holder.id}.")
        val response = holderServicePort.create(holder).toCreateResponse()
        logger.info("Holder created with id ${response.id}")
        MDC.clear()
        return response
    }

    @GetMapping("{cpf}")
    suspend fun get(@PathVariable cpf: String): CreateHolderResponse {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        logger.info("Initializing GET request to /holders/{cpf} endpoint with cpf: $cpf.")
        val response = holderServicePort.get(cpf).toCreateResponse()
        logger.info("Holder returned with id: ${response.id}")
        MDC.clear()
        return response
    }

    @GetMapping
    suspend fun getAll(): List<Holder> {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        logger.info("Initializing GET request to /holders endpoint.")
        val response = holderServicePort.getAll()
        logger.info("List of holders returned with success.")
        MDC.clear()
        return response
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: UUID) : ResponseEntity<Unit> {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("holder_id", id.toString())

        logger.info("Initializing DELETE request to /holders/{id} endpoint with id: $id.")
        holderServicePort.delete(id)
        logger.info("Holder deleted with id: $id.")
        MDC.clear()
        return ResponseEntity.ok(Unit)
    }
}