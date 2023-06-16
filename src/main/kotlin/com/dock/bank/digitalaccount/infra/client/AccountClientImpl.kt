package com.dock.bank.digitalaccount.infra.client

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exceptions.GeneralException

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class AccountClientImpl(
    @Qualifier("Account") private val restTemplate: RestTemplate,
    @Value("\${account-service.url}") private val url: String
): AccountClient {

    companion object {
        private val logger = LoggerFactory.getLogger(AccountClientImpl::class.java)
    }

    override fun generateAccount(holder: Holder, auth: String) : ResponseEntity<Account> {
        try {
            val headers = HttpHeaders()
            headers.add("Authorization", auth)
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity(holder, headers)
            logger.info("Creating account for holder with id ${holder.id} and name ${holder.name}")
            return restTemplate.postForEntity(url, request, Account::class.java)
        } catch (e: Exception) {
            throw GeneralException("")
        }
    }
}