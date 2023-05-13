package com.dock.bank.digitalaccount.core.port.queue

interface QueueProducer {
    suspend fun publish(message: String)
}