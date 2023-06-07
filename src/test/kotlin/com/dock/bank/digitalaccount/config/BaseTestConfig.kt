package com.dock.bank.digitalaccount.config

import com.dock.bank.digitalaccount.DigitalaccountApplication
import io.restassured.RestAssured
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RestAssuredConfig
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.Network

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(initializers = [BaseTestConfig.JdbcContainerInitializer::class])
@SpringBootTest(
    classes = [DigitalaccountApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseTestConfig {

    @LocalServerPort
    private val port: Int = 0

    @BeforeAll
    fun beforeAll() {
        RestAssured.config =
            RestAssuredConfig.config()
                .objectMapperConfig(ObjectMapperConfig()
                    .jackson2ObjectMapperFactory(ObjectMapperConfigurator.objectMapperFactory))
    }

    companion object {
        private val logger = LoggerFactory.getLogger("BaseTestConfig")
        private val postgresContainer = DatabaseContainer("postgres:12").apply { start() }

        fun getDatabaseUrl() : String {
            val dbHost = postgresContainer.containerIpAddress
            val dbPort = postgresContainer.getMappedPort(5432)
            return """jdbc:postgresql://$dbHost:$dbPort/${DatabaseContainer.DATABASE}"""
        }
    }

    init {
        logger.info("Starting test containers")
        val network = Network.newNetwork()
        logger.info("creating a new docker network '${network.id}'")
    }

    class JdbcContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(context: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=${getDatabaseUrl()}",
                "spring.datasource.hikari.schema=public",
                "spring.datasource.name=${DatabaseContainer.DATABASE}",
                "spring.datasource.username=${DatabaseContainer.USER}",
                "spring.datasource.password=${DatabaseContainer.PASSWORD}"
            ).applyTo(context.environment)
        }
    }
}
