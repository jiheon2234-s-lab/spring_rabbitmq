package com.hellj.rabbit.step5

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class LogPublisher(
    private val rabbitTemplate: RabbitTemplate
) {

    fun publish(routingKey: String, message: String) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, routingKey, message)
        logger.info { "message published : $routingKey: $message" }
    }

}