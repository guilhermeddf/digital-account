package com.dock.bank.digitalaccount.configuration

import com.dock.bank.digitalaccount.core.service.*
import com.dock.bank.digitalaccount.ports.api.*
import com.dock.bank.digitalaccount.ports.spi.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
open class DigitalAccountConfig {

    @Bean
    open fun accountGeneratorService(): AccountGeneratorServiceImpl {
        return AccountGeneratorServiceImpl()
    }

    @Bean
    open fun accountServicePort(
        accountDatabasePort: AccountDatabasePort,
        holderDatabasePort: HolderDatabasePort,
        accountGenerator: AccountGeneratorServicePort
    ): AccountServicePort {
        return AccountServicePortImpl(accountDatabasePort, holderDatabasePort, accountGenerator)
    }

    @Bean
    open fun holderServicePort(holderDatabasePort: HolderDatabasePort): HolderServicePort {
        return HolderServicePortImpl(holderDatabasePort)
    }

    @Bean
    open fun transactionServicePort(
        transactionDatabasePort: TransactionDatabasePort,
        accountDatabasePort: AccountDatabasePort
    ): TransactionServicePort {
        return TransactionServicePortImpl(transactionDatabasePort, accountDatabasePort)
    }
    
    @Bean
    open fun transactionFactoryServicePort(accountDatabasePort: AccountDatabasePort): TransactionFactoryServicePort {
        return TransactionFactoryImpl(accountDatabasePort)
    }

//    @Bean
//    open fun loginServicePort(authenticationService: AuthenticationServicePort): LoginServicePort {
//        return LoginServicePortImpl(authenticationService)
//    }

    @Bean
    open fun userServicePort(userDatabasePort: UserDatabasePort): UserServicePort {
        return UserServicePortImpl(userDatabasePort)
    }
}