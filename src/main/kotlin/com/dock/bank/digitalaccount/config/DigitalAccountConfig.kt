package com.dock.bank.digitalaccount.config

import com.dock.bank.digitalaccount.core.usecase.AccountGeneratorImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DigitalAccountConfig {

    @Bean
    fun accountGenerator(): AccountGeneratorImpl {
        return AccountGeneratorImpl()
    }
}