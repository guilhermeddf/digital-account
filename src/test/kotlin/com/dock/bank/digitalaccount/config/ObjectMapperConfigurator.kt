package com.dock.bank.digitalaccount.config


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy

import com.fasterxml.jackson.module.kotlin.KotlinModule

import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory

object ObjectMapperConfigurator {


    private val objectMapper = ObjectMapper().registerModule(KotlinModule())
    init {
        objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    }
    private fun get(): ObjectMapper {
        return objectMapper
    }

    val objectMapperFactory = Jackson2ObjectMapperFactory { _, _ -> get() }
}





