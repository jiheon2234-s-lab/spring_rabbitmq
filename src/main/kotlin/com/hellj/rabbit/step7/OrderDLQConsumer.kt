package com.hellj.rabbit.step7

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OrderDLQConsumer(
    private val rabbitTemplate: RabbitTemplate
) {

    @RabbitListener(queues = [RabbitMQConfig.DLQ])
    fun process(message: String) {
        logger.info { "DLQ Message Received: $message" }

        try {
            val fixMessage = "success"

            rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                "order.completed.shipping",
                fixMessage
            )
            logger.info { "DLQ Message Sent: $fixMessage" }
        } catch (e: Exception) {
            logger.error { "### [DLQ Consumer Error] ${e.message}" }
        }
    }
}