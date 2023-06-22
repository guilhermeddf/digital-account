package com.dock.bank.digitalaccount.sqs

import com.dock.bank.digitalaccount.core.domain.Holder
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component


@Component
class SqsHolderConsumerImpl {

    @SqsListener(
        value = ["http://localhost:4566/000000000000/sample-queue"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS
    )
    fun receivePojo(@Headers sqsMessageHeader: Map<String?, String?>?, @Payload pojo: Holder?) {
        println(pojo)
    }
}