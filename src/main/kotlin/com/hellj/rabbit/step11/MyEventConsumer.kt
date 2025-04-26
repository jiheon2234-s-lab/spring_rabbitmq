package com.hellj.rabbit.step11

import com.hellj.rabbit.step11.entity.MyEventEntity
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class MyEventConsumer {

    @RabbitListener(queues = [RabbitMQConfig.QUEUE_NAME])
    fun consumeMessage(myEvent: MyEventEntity) {
        logger.info { "consume event : $myEvent" }
    }
}