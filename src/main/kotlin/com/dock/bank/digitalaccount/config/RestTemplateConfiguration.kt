package com.dock.bank.digitalaccount.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class RestTemplateConfiguration(
    @Value("\${account-service.timeout}") private val timeout: Long
) {
    @Bean("Account")
    fun restTemplateAccount(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        return restTemplateBuilder
            .setConnectTimeout(Duration.ofMillis(timeout))
            .setReadTimeout(Duration.ofMillis(timeout))
            .build()
    }
}
