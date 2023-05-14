package com.dock.bank.digitalaccount.infra.rest.controllers


import com.dock.bank.digitalaccount.config.BaseTestConfig
import com.dock.bank.digitalaccount.core.exceptions.ErrorMessage
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import com.dock.bank.digitalaccount.infra.rest.dto.CreateHolderResponse
import com.dock.bank.digitalaccount.utils.buildCreateHolderRequest
import com.dock.bank.digitalaccount.utils.cpfGenerator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class HolderControllerTest : BaseTestConfig() {

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
    protected lateinit var holderPersistence: HolderPersistence

    @Test
    @DisplayName("should save a holder with success")
    fun`should save a holder with success`() {

        val fakeCreateHolderRequest = buildCreateHolderRequest(cpf = cpfGenerator())
        val jsonBodyRequest = getMapper().writeValueAsString(fakeCreateHolderRequest)

        val stringResponse = RestAssured.given()
            .request()
            .contentType(ContentType.JSON)
            .accept(ContentType.ANY)
            .and()
            .body(jsonBodyRequest)
            .`when`()
            .post("/holders")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract().body().asString()

        val objectResponse = getMapper().readValue(stringResponse, CreateHolderResponse::class.java)

        assertAll(
            Executable { assertNotNull(objectResponse) },
            Executable { assertNotNull(objectResponse.id) },
            Executable { assertEquals(fakeCreateHolderRequest.cpf, objectResponse.cpf) },
            Executable { assertEquals(fakeCreateHolderRequest.name, objectResponse.name) },
        )
    }

    @Test
    @DisplayName("should not save a holder with success")
    fun`should not save a holder with success`() {
        val fakeCreateHolderRequest = buildCreateHolderRequest(cpf = "53767283310")
        val jsonBodyRequest = getMapper().writeValueAsString(fakeCreateHolderRequest)

        val stringResponse = RestAssured.given()
            .request()
            .contentType(ContentType.JSON)
            .accept(ContentType.ANY)
            .and()
            .body(jsonBodyRequest)
            .`when`()
            .post("/holders")
            .then()
            .statusCode(HttpStatus.CONFLICT.value())
            .contentType(ContentType.JSON)
            .extract().body().asString()

        val objectResponse = getMapper().readValue(stringResponse, ErrorMessage::class.java)

        assertAll(
            Executable { assertNotNull(objectResponse) },
            Executable { assertEquals(objectResponse.message, "Holder already exists.") },
            Executable { assertEquals(objectResponse.error, "com.dock.bank.digitalaccount.core.exceptions.ResourceAlreadyExistsException") },
            Executable { assertEquals(objectResponse.status, 409) }
        )
    }
}