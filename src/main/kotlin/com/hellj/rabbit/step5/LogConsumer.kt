package com.hellj.rabbit.step5

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class LogConsumer {

    @RabbitListener(queues = [RabbitMQConfig.ERROR_QUEUE])
    fun consumeError(message: String) {
        logger.error { "[ERROR]: $message" }
    }

    @RabbitListener(queues = [RabbitMQConfig.WARN_QUEUE])
    fun consumeWarn(message: String) {
        logger.warn { "[WARN]: $message" }
    }

    @RabbitListener(queues = [RabbitMQConfig.INFO_QUEUE])
    fun consumeInfo(message: String) {
        logger.info { "[INFO]: $message" }
    }

    @RabbitListener(queues = [RabbitMQConfig.ALL_LOG_QUE])
    fun consumeAllLogs(message: String) {
        logger.info { "[ALL LOGS]: $message" }
    }
}