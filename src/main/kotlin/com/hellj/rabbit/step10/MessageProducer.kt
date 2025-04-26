package com.hellj.rabbit.step10

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

@Component
class MessageProducer(
    private val rabbitTemplate: RabbitTemplate,
    private val stockRepository: StockRepository
) {
    @Transactional
    fun sendMessage(stockEntity: StockEntity, testCase: Boolean) {
        val entity = stockEntity.run {
            processed = true
            createdAt = Instant.now()
            stockRepository.save(this)
        }

        if (stockEntity.userId.isEmpty()) {
            throw RuntimeException("User Id is required")
        }

        try {
            val correlationData = CorrelationData(entity.id.toString())
            rabbitTemplate.convertAndSend(
                if (testCase) "nonExistentExchange" else RabbitMQConfig.EXCHANGE_NAME,
                if (testCase) "invalidRoutingKey" else RabbitMQConfig.ROUTING_KEY,
                entity,
                correlationData
            )
            if (correlationData.future.get(5, TimeUnit.SECONDS).isAck) {
                logger.info { "[producer correlationData] 성공 $entity" }
                entity.processed = true
                stockRepository.save(entity)
            } else {
                throw RuntimeException("# confirm 실패 - 롤백")
            }
        } catch (e: Exception) {
            logger.info { "[producer exception fail] : $e" }
            throw RuntimeException(e)
        }

    }
}