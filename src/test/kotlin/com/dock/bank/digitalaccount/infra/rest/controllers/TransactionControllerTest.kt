package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.config.BaseTestConfig
import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.core.exceptions.ErrorMessage
import com.dock.bank.digitalaccount.infra.database.repository.TransactionRepository
import com.dock.bank.digitalaccount.infra.rest.dto.CreateTransactionResponse
import com.dock.bank.digitalaccount.utils.buildCreateTransactionRequest
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.math.BigInteger
import java.util.*

class TransactionControllerTest : BaseTestConfig() {

    companion object {
        private fun getMapper(): ObjectMapper {
            val objectMapper: ObjectMapper = jacksonObjectMapper()
            objectMapper.registerModule(JavaTimeModule())
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            objectMapper.registerModule(SimpleModule())
            objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
            return objectMapper
        }
    }

    @Autowired
    protected lateinit var transactionRepository: TransactionRepository

    @Test
    @DisplayName("should save a credit transaction with success")
    fun`should save a credit transaction with success`() {
        val fakeCreateTransactionRequest = buildCreateTransactionRequest()
        val jsonBodyRequest = getMapper().writeValueAsString(fakeCreateTransactionRequest)

        val stringResponse = RestAssured.given()
            .pathParam("accountId", UUID.fromString("fc0a369d-615a-4119-aa75-b3d35119aa4d") )
            .request()
            .contentType(ContentType.JSON)
            .accept(ContentType.ANY)
            .and()
            .body(jsonBodyRequest)
            .`when`()
            .post("/transactions/{accountId}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract().body().asString()

        val objectResponse = getMapper().readValue(stringResponse, CreateTransactionResponse::class.java)

        assertAll(
            Executable { assertNotNull(objectResponse) },
            Executable { assertEquals(fakeCreateTransactionRequest.amount, objectResponse.amount) },
            Executable { assertEquals(fakeCreateTransactionRequest.type, objectResponse.type)}
        )
    }

    @Test
    @DisplayName("should save a debit transaction with success")
    fun`should save a debit transaction with success`() {
        val fakeCreateTransactionRequest = buildCreateTransactionRequest(
            amount = BigInteger.valueOf(50),
            type = TransactionType.DEBIT)

        val jsonBodyRequest = getMapper().writeValueAsString(fakeCreateTransactionRequest)

        val stringResponse = RestAssured.given()
            .pathParam("accountId", UUID.fromString("fc0a369d-615a-4119-aa75-b3d35119aa4d") )
            .request()
            .contentType(ContentType.JSON)
            .accept(ContentType.ANY)
            .and()
            .body(jsonBodyRequest)
            .`when`()
            .post("/transactions/{accountId}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract().body().asString()

        val objectResponse = getMapper().readValue(stringResponse, CreateTransactionResponse::class.java)

        assertAll(
            Executable { assertNotNull(objectResponse) },
            Executable { assertEquals(fakeCreateTransactionRequest.amount, objectResponse.amount) },
            Executable { assertEquals(fakeCreateTransactionRequest.type, objectResponse.type)}
        )
    }

    @Test
    @DisplayName("should not save a debit transaction with success")
    fun`should not save an debit transaction with success`() {
        val fakeCreateTransactionRequest = buildCreateTransactionRequest(
            amount = BigInteger.valueOf(500),
            type = TransactionType.DEBIT)

        val jsonBodyRequest = getMapper().writeValueAsString(fakeCreateTransactionRequest)

        val stringResponse = RestAssured.given()
            .pathParam("accountId", UUID.fromString("fc0a369d-615a-4119-aa75-b3d35119aa4d") )
            .request()
            .contentType(ContentType.JSON)
            .accept(ContentType.ANY)
            .and()
            .body(jsonBodyRequest)
            .`when`()
            .post("/transactions/{accountId}")
            .then()
            .statusCode(HttpStatus.CONFLICT.value())
            .contentType(ContentType.JSON)
            .extract().body().asString()

        val objectResponse = getMapper().readValue(stringResponse, ErrorMessage::class.java)

        assertAll(
            Executable { assertNotNull(objectResponse) },
            Executable { assertEquals(objectResponse.message, "Insufficient balance.") },
            Executable { assertEquals(objectResponse.error, "com.dock.bank.digitalaccount.core.exceptions.DomainException") },
            Executable { assertEquals(objectResponse.status, 409) }
        )
    }
}