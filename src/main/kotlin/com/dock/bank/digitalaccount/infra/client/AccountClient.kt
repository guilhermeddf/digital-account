package com.dock.bank.digitalaccount.infra.client

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exceptions.ErrorMessage
import com.dock.bank.digitalaccount.core.exceptions.GeneralException
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.infra.gateway.AccountGateway
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

@Component
class AccountClient(
    private val objectMapper: ObjectMapper,
    private val restTemplate: RestTemplate,
    @Value("\${account-service.url}") private val url: String,
    @Value("\${account-service.auth}") private val auth: String,
): AccountGateway {

    val URL = "http://localhost:3001"
    val PATH = "/accounts/"

    override fun generateAccount(holder: Holder) : Account {
        try {
            val headers = HttpHeaders()
            headers.add("Authorization", auth)
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity(holder, headers)
            val response = restTemplate.postForEntity(url, request, Account::class.java)
            return response.body!!
        } catch (e: Exception) {
            throw GeneralException("")
        }
    }
}