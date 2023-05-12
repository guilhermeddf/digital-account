package com.dock.bank.digitalaccount.infra.sqs

import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class SQSConsumer {
    companion object {
        private val logger = LoggerFactory.getLogger(SQSConsumer::class.java)
    }

    @SqsListener("\${cloud.aws.end-point.queue-name}")
    fun listener(message: String) {
        try {
            logger.info("Message received: $message")
        }catch (e: Exception) {
            logger.error("Error receiving Message.")
        }
    }

}