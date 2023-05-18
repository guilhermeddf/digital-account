package com.dock.bank.digitalaccount.config

import com.dock.bank.digitalaccount.core.port.adapter.AccountGenerator
import com.dock.bank.digitalaccount.core.port.persistence.AccountPersistence
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import com.dock.bank.digitalaccount.core.port.persistence.TransactionPersistence
import com.dock.bank.digitalaccount.core.usecase.AccountGeneratorImpl
import com.dock.bank.digitalaccount.core.usecase.AccountUseCaseImpl
import com.dock.bank.digitalaccount.core.usecase.HolderUseCaseImpl
import com.dock.bank.digitalaccount.core.usecase.TransactionUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DigitalAccountConfig {

    @Bean
    fun accountGenerator(): AccountGeneratorImpl {
        return AccountGeneratorImpl()
    }

    @Bean
    fun accountUseCase(
        accountPersistence: AccountPersistence,
        holderPersistence: HolderPersistence,
        accountGenerator: AccountGenerator
    ): AccountUseCaseImpl {
        return AccountUseCaseImpl(accountPersistence, holderPersistence, accountGenerator)
    }

    @Bean
    fun holderUseCase(holderPersistence: HolderPersistence): HolderUseCaseImpl {
        return HolderUseCaseImpl((holderPersistence))
    }

    @Bean
    fun transactionUseCase(
        transactionPersistence: TransactionPersistence,
        accountPersistence: AccountPersistence
    ): TransactionUseCaseImpl {
        return TransactionUseCaseImpl(transactionPersistence, accountPersistence)
    }
}