package com.hellj.rabbit.step7

import com.rabbitmq.client.Channel
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import java.io.IOException

private val logger = KotlinLogging.logger {}


@Component
class OrderConsumer {
    private val MAX_RETRIES = 3
    private var retryCount = 0

    @RabbitListener(
        queues = [RabbitMQConfig.ORDER_COMPLETED_QUEUE],
        containerFactory = "rabbitListenerContainerFactory"
    )
    fun processOrder(message: String, channel: Channel, @Header("amqp_deliveryTag") tag: Long) {
        try {
            // 실패 유발
            if (message.equals("fail", ignoreCase = true)) {
                if (retryCount < MAX_RETRIES) {
                    logger.error { "#### Fail & Retry = $retryCount" }
                    retryCount++
                    throw RuntimeException(message)
                } else {
                    logger.error { "#### 최대 횟수 초과, DLQ 이동 시킴" }
                    retryCount = 0
                    channel.basicNack(tag, false, false)
                    return
                }
            }
            logger.info { "# 성공 : $message" }
            channel.basicAck(tag, false)
            retryCount = 0
        } catch (e: Exception) {
            logger.error { "# error 발생 : ${e.message}" }
            try {
                channel.basicReject(tag, true)
            } catch (e1: IOException) {
                logger.error { "# fail & reject message : ${e1.message}" }
            }
        }
    }
}