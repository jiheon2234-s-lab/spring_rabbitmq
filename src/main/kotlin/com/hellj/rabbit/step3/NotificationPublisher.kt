package com.hellj.rabbit.step3

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class NotificationPublisher(
    private val rabbitTemplate: RabbitTemplate
) {
    fun publish(message: String) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", message)
        logger.info { "[#] Published Notification: $message" }
    }
}