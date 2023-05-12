package com.dock.bank.digitalaccount.infra.sqs


import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component


@Component
class SQSProducer(
    @Value("\${cloud.aws.end-point.uri}") private val sqsUrl: String,
    private val queueMessagingTemplate: QueueMessagingTemplate
) {

    fun sendMessage(message: String) {
        queueMessagingTemplate.send(sqsUrl, MessageBuilder.withPayload(message).build())
    }
}

