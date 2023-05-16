package com.dock.bank.digitalaccount.infra.secretsManager.converter

import com.dock.bank.digitalaccount.core.domain.Credentials
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.infra.postgres.model.HolderTable
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.stereotype.Component

@Component
class JsonConverter(private val objectMapper: ObjectMapper){

    fun toCredentials(data: String): Credentials {
        try {
            return objectMapper.readValue(data, Credentials::class.java)
        } catch (e: Exception){
            throw Exception("Error trying to parse a credentials Json into a object.", e)
        }
    }

    fun toHolderTable(data: String): HolderTable {
        try {
            return objectMapper.readValue(data, HolderTable::class.java)
        } catch (e: Exception){
            throw Exception("Error trying to parse a credentials Json into a object.", e)
        }
    }
}

fun String.toHolder(): Holder {
    val objectMapper = ObjectMapper().registerModule(KotlinModule())
    try {
        return objectMapper.readValue(this, Holder::class.java)
    } catch (e: Exception){
        throw Exception("Error trying to parse a holder Json into a object.", e)
    }
}

