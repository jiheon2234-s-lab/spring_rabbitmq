package com.hellj.rabbit.step8

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OrderDeadLetterRetry(
    private var rabbitTemplate: RabbitTemplate
) {
    
    @RabbitListener(queues = [RabbitMQConfig.DLQ])
    fun processDeadLetter(message: String) {
        var message = message
        logger.info { "[DLQ Received]: $message" }

        try {
            // "fail" 메시지를 수정하여 성공적으로 처리되도록 변경
            if ("fail".equals(message, ignoreCase = true)) {
                message = "success"
                logger.info { "[DLQ] Message fixed: $message" }
            } else {
                // 이미 수정된 메시지는 다시 처리하지 않음
                System.err.println("[DLQ] Message already fixed. Ignoring: $message")
                return
            }

            // 수정된 메시지를 원래 큐로 다시 전송
            rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_TOPIC_EXCHANGE, "order.completed", message)
            logger.info { "[DLQ] Message requeued to original queue: $message" }
        } catch (e: Exception) {
            logger.error { "[DLQ] Failed to reprocess message: " + e.message }
        }
    }
}