package com.hellj.rabbit.step9

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.time.Instant

private val logger = KotlinLogging.logger {}

@Component
class MessageConsumer(
    private val stockRepository: StockRepository
) {

    @RabbitListener(queues = ["transactionQueue"])
    fun receiveTranSaction(stockEntity: StockEntity) {
        logger.info { "# recieved message = $stockEntity" }

        try {
            stockRepository.save(
                stockEntity.apply {
                    processed = true
                    updatedAt = Instant.now()
                }
            ).also {
                logger.info { "# StockEntity 저장 완료 $stockEntity" }
            }
        } catch (e: Exception) {
            logger.info { "# Entity 수정 에러 ${e.message}" }
            throw e
        }
    }


}