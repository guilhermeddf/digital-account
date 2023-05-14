package com.dock.bank.digitalaccount.infra.credentials.converter

import com.dock.bank.digitalaccount.core.domain.Credentials
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class JsonConverter(private val objectMapper: ObjectMapper){

    fun toEntity(data: String): Credentials {
        try {
            return objectMapper.readValue(data, Credentials::class.java)
        } catch (e: Exception){
            throw Exception("Error trying to parse a Json into a object.", e)
        }

    }
}
