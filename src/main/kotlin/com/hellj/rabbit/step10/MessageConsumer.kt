package com.hellj.rabbit.step10

import com.rabbitmq.client.Channel
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.Instant

private val logger = KotlinLogging.logger {}


@Component
class MessageConsumer(
    private val stockRepository: StockRepository
) {
    @RabbitListener(queues = [RabbitMQConfig.QUEUE_NAME], containerFactory = "rabbitListenerContainerFactory")
    fun receiveMessage(stock: StockEntity, @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long, channel: Channel) {

        try {
            logger.info { "[Consumer] $stock" }
            Thread.sleep(200)
            stockRepository.findById(stock.id!!).orElseThrow { RuntimeException("Stock not found") }
                .let {
                    it.updatedAt = Instant.now()
                    stockRepository.save(it)
                    logger.info { "[Save Entity Consumer] $it" }
                }

            channel.basicAck(deliveryTag, false)
        } catch (e: Exception) {
            logger.info { "[Consumer Error] ${e.message}" }
            try {
                channel.basicNack(deliveryTag, false, false)
            } catch (ex: IOException) {
                logger.error { "[Consumer send nack] ${ex.message}" }
            }
        }
    }
}