package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.config.BaseTestConfig
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresAccountRepository
import com.dock.bank.digitalaccount.infra.rest.dto.CreateAccountResponse
import com.dock.bank.digitalaccount.utils.buildHolder
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.http.Header
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class AccountControllerTest : BaseTestConfig() {

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
    protected lateinit var postgresAccountRepository: PostgresAccountRepository

    @Test
    @DisplayName("should save an account with success")
    fun`should save an account with success`() {

        val fakeHolder = buildHolder(cpf = "53767283310")

        val stringResponse = RestAssured.given()
            .header(Header("holder-cpf", fakeHolder.cpf))
            .request()
            .accept(ContentType.ANY)
            .`when`()
            .post("/accounts")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract().body().asString()

        val objectResponse = getMapper().readValue(stringResponse, CreateAccountResponse::class.java)

        assertAll(
            Executable { Assertions.assertNotNull(objectResponse) },
            Executable { Assertions.assertNotNull(objectResponse.id) },
        )
   }
}
