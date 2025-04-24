package com.hellj.rabbit.step8

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OrderConsumer(
    private val rabbitTemplate: RabbitTemplate,
) {

    private var retryCount = 0

    @RabbitListener(queues = [RabbitMQConfig.ORDER_COMPLETED_QUEUE])
    fun processMessage(message: String) {
        println("Received message: " + message + "count : " + retryCount++)
        if ("fail" == message) {
            throw RuntimeException("- Processing failed. Retry")
        }
        println("Message processed successfully: $message")
        retryCount = 0
    }
}