package com.dock.bank.digitalaccount.core.port.messaging

interface BrokerProducer {
    suspend fun publish(message: String)
}