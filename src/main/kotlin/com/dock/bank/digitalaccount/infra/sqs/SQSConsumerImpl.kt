package com.dock.bank.digitalaccount.infra.sqs

import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class SQSConsumerImpl {
    companion object {
        private val logger = LoggerFactory.getLogger(SQSConsumerImpl::class.java)
    }

    @SqsListener("\${cloud.aws.sqs.end-point.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun receive(message: String) {
        try {
            logger.info("Message received: $message")
        } catch (e: Exception) {
            throw Exception("Error trying to receive message from queue.")
        }
    }
}