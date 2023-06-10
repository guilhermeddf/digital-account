/* package com.dock.bank.digitalaccount.infra.sqs

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.dock.bank.digitalaccount.core.port.messaging.QueueProducer
import com.dock.bank.digitalaccount.core.usecase.AccountUseCaseImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SQSProducerImpl(
    private val awsSQS: AmazonSQS,
    @Value("\${cloud.aws.sqs.end-point.uri}") private val sqsUrl: String
): QueueProducer {
    companion object {
        private val logger = LoggerFactory.getLogger(AccountUseCaseImpl::class.java)
    }

    override suspend fun publish(message: String) {
        try {
            val sendMessage = SendMessageRequest()
                .withQueueUrl(sqsUrl)
                .withMessageBody(message)
            awsSQS.sendMessage(sendMessage)
            logger.info("Message publish with success. Message: $message")
        } catch (e: Exception){
            throw Exception("Error trying to publish queue message.", e)
        }
    }
}

 */