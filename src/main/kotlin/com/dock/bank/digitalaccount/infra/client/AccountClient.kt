package com.dock.bank.digitalaccount.infra.client

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.exceptions.ErrorMessage
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.infra.exceptions.RestClientException
import com.dock.bank.digitalaccount.infra.gateway.AccountGateway
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

@Component
class AccountClient(
    private val objectMapper: ObjectMapper
): AccountGateway {

    val URL = "http://localhost:3001"
    val PATH = "/accounts/"
    val AUTH = "xyz"

    override fun createAccount(holderCpf: String) : Account {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder(URI.create(URL+PATH+holderCpf))
            .header("Authorization", AUTH)
            .build()

        val response = client.send(request, BodyHandlers.ofString())
        val statusCode = HttpStatus.valueOf(response.statusCode())

        if(!statusCode.is2xxSuccessful) {

            val algo  = objectMapper.readValue(response.body(), ErrorMessage::class.java)

            responseIsNotFound(algo)
            responseIsForbidden(algo)
            responseIsAuthorized(algo)

            throw RestClientException(message = algo.message)
        }

        return objectMapper.readValue(response.body(), Account::class.java)
    }

    fun responseIsNotFound(e: ErrorMessage) {
        if (e.status == 404) {
            throw ResourceNotFoundException("Account not found.")
        }
    }

    fun responseIsForbidden(e: ErrorMessage) {
        if (e.status == 403) {
            throw RestClientException("HTTP external service error response.")
        }
    }

    fun responseIsAuthorized(e: ErrorMessage) {
        if (e.status == 401) {
            throw RestClientException("HTTP external service error response.")
        }
    }
}