package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.core.port.adapter.TransactionFactory
import com.dock.bank.digitalaccount.core.port.adapter.TransactionUseCase
import com.dock.bank.digitalaccount.infra.rest.converter.toCreateResponse
import com.dock.bank.digitalaccount.infra.rest.converter.toRetrieveResponse
import com.dock.bank.digitalaccount.infra.rest.dto.CreateTransactionRequest
import com.dock.bank.digitalaccount.infra.rest.dto.CreateTransactionResponse
import com.dock.bank.digitalaccount.infra.rest.dto.RetrieveTransactionResponse
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
    private val transactionUseCase: TransactionUseCase,
    private val transactionFactory: TransactionFactory
) {

    @PostMapping("/{accountId}")
    suspend fun create(
        @RequestBody createTransactionRequest: CreateTransactionRequest,
        @PathVariable accountId: UUID
    ) : CreateTransactionResponse {

        val transaction = transactionFactory.generateTransaction(
            amount = createTransactionRequest.amount,
            type = createTransactionRequest.type,
            accountId = accountId
        )
        return transactionUseCase.create(transaction).toCreateResponse()
    }

    @GetMapping("/{accountId}")
     suspend fun retrieveTransactions(
        @PathVariable accountId: UUID,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) finishDate: LocalDate,
    ) : List<RetrieveTransactionResponse> {
         return transactionUseCase.getTransactions(
             accountId = accountId,
             startDate = startDate,
             finishDate = finishDate,
         ).map { transaction -> transaction.toRetrieveResponse() }
    }
}