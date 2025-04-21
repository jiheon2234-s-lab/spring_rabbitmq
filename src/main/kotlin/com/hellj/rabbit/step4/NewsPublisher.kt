package com.hellj.rabbit.step4

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class NewsPublisher(
    private val rabbitTemplate: RabbitTemplate
) {
    private fun publishMessage(news: String, messageSuffix: String): String {
        val message = news + messageSuffix
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE_FOR_NEWS, news, message)
        logger.info { "News Published: $message" }
        return message
    }

    fun publish(news: String): String {
        return publishMessage(news, " 관련 새 소식이 있어요!")
    }

    fun publishAPI(news: String): String {
        return publishMessage(news, " - 관련 새 소식이 나왔습니다. (API)")
    }
}