package com.hellj.rabbit.step1

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class Sender(
    private val rabbitTemplate: RabbitTemplate
) {
    fun send(message: String) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message)
        println("[#] Sent: ${message}")
    }
}