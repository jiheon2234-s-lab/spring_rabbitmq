package com.hellj.rabbit.step3

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class NotificationSubscriber(
    private val simpleMessageTemplate: SimpMessagingTemplate
) {
    companion object {
        val CLIENT_URL = "/topic/notifications"
    }

    @RabbitListener(queues = [RabbitMQConfig.QUEUE_NAME])
    fun subscriber(message: String) {
        // RabbitMQ Queue에서 메시지 수신
        // String message = (String) rabbitTemplate.receiveAndConvert(RabbitMQConfig.QUEUE_NAME);
        logger.info { "Recieved Notification: $message" }
        // convertAndSend를 통해 특정 경로로 메시지 전달
        simpleMessageTemplate.convertAndSend(CLIENT_URL, message)
    }
}