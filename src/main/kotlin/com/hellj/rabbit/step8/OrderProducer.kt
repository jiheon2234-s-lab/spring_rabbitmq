package com.hellj.rabbit.step8

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OrderProducer(
    private val rabbitTemplate: RabbitTemplate
) {

    fun sendShipping(message: String) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ORDER_TOPIC_EXCHANGE,
            "order.completed",
            message
        )

        logger.info { "[주문 완료, 배송 지시 메시지 생성 : $message" }
    }
}