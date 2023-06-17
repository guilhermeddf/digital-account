package com.dock.bank.digitalaccount.api.v1.controller

import com.dock.bank.digitalaccount.api.v1.mapper.toCreateResponse
import com.dock.bank.digitalaccount.api.v1.mapper.toRetrieveResponse
import com.dock.bank.digitalaccount.api.v1.request.CreateTransactionRequest
import com.dock.bank.digitalaccount.api.v1.response.CreateTransactionResponse
import com.dock.bank.digitalaccount.api.v1.response.RetrieveTransactionResponse
import com.dock.bank.digitalaccount.ports.api.TransactionFactoryServicePort
import com.dock.bank.digitalaccount.ports.api.TransactionServicePort
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/transactions")
class TransactionController(
    private val transactionServicePort: TransactionServicePort,
    private val transactionFactoryServicePort: TransactionFactoryServicePort
) {
    companion object {
        private val logger = LoggerFactory.getLogger(TransactionController::class.java)
    }

    @PostMapping("/{accountId}")
    suspend fun create(
        @RequestBody createTransactionRequest: CreateTransactionRequest,
        @PathVariable accountId: UUID
    ) : CreateTransactionResponse {
        val requestId = UUID.randomUUID()

        val transaction = transactionFactoryServicePort.generateTransaction(
            amount = createTransactionRequest.amount,
            type = createTransactionRequest.type,
            accountId = accountId
        )

        MDC.put("request_id", requestId.toString())
        MDC.put("transaction_id", transaction.id.toString())
        MDC.put("account_id", transaction.account.id.toString())

        logger.info("Initializing POST request to /transactions/{accountId} endpoint with account id: ${accountId}.")
        val response = transactionServicePort.create(transaction).toCreateResponse()
        logger.info("Transaction created with id: ${response.id}.")
        MDC.clear()
        return response
    }

    @GetMapping("/{accountId}")
     suspend fun retrieveTransactions(
        @PathVariable accountId: UUID,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) finishDate: LocalDate,
    ) : List<RetrieveTransactionResponse> {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("account_id", accountId.toString())

        logger.info("Initializing GET request to /transactions/{accountId} endpoint with account id: ${accountId}.")
        val response = transactionServicePort.getTransactions(
            accountId = accountId,
            startDate = startDate,
            finishDate = finishDate,
        ).map { transaction -> transaction.toRetrieveResponse() }
        MDC.clear()
        logger.info("List of transactions returned with success.")
        return response
    }
}