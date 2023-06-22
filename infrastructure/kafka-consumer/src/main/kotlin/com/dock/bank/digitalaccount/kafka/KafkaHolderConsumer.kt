package com.dock.bank.digitalaccount.kafka

import com.dock.bank.digitalaccount.core.domain.Holder
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service
import java.util.concurrent.CountDownLatch

@Service
class KafkaHolderConsumer {

    companion object {
        private val logger = LoggerFactory.getLogger(KafkaHolderConsumer::class.java)
    }

    @KafkaListener(id = "holder-listener", topics = ["\${spring.kafka.topic.name}"], groupId = "group_id")
    fun consume(@Payload consumerRecord: ConsumerRecord<String, Holder>, ack: Acknowledgment) {
        val countDown = CountDownLatch(1)
        try {
            logger.info("Headers: ${consumerRecord.headers()}")
            logger.info("Key: ${consumerRecord.key()}")
            logger.info("Holder: ${consumerRecord.value()}")

            val payload = consumerRecord.value()
            countDown.countDown()
        } finally {
            ack.acknowledge()
        }
    }
}