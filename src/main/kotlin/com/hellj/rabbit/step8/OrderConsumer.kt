package com.hellj.rabbit.step8

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.retry.RetryContext
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OrderConsumer(
    private val rabbitTemplate: RabbitTemplate,
    private val retryTemplate: RetryTemplate
) {


    @RabbitListener(queues = [RabbitMQConfig.ORDER_COMPLETED_QUEUE])
    fun consume(message: String) {
        retryTemplate.execute<Any?, RuntimeException> { context: RetryContext ->
            try {
                logger.info { """# 리시브 메시지 : $message # retry : ${context.retryCount}""" }
                // 실패 조건
                if ("fail".equals(message, ignoreCase = true)) {
                    throw RuntimeException(message)
                }
                logger.info { "# 메시지 처리 성공 $message" }
            } catch (e: Exception) {
                if (context.retryCount >= 2) {
                    rabbitTemplate.convertAndSend(
                        RabbitMQConfig.ORDER_TOPIC_DLX,
                        RabbitMQConfig.DEAD_LETTER_ROUTING_KEY, message
                    )
                } else {
                    throw e
                }
            }
            null
        }
    }
}