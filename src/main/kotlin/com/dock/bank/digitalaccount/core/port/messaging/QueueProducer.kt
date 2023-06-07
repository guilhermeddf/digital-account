package com.dock.bank.digitalaccount.core.port.messaging

interface QueueProducer {
    suspend fun publish(message: String)
}