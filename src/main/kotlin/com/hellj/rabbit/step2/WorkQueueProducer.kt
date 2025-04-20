package com.hellj.rabbit.step2

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Configuration

private val logger = KotlinLogging.logger {}

@Configuration
class WorkQueueProducer(
    private val rabbitTemplate: RabbitTemplate
) {

    fun sendWorkQueue(workQueueMessage: String, duration: Int) {
        val message = "$workQueueMessage|$duration"
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message)

        logger.info { "Sent workQueue $message" }
    }


}