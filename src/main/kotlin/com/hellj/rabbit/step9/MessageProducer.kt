package com.hellj.rabbit.step9

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

private val logger = KotlinLogging.logger {}

@Component
class MessageProducer(
    private val stockRepository: StockRepository,
    private val rabbitTemplate: RabbitTemplate,
) {

    @Transactional
    fun sendMessage(stockEntity: StockEntity, testCase: String) {
        rabbitTemplate.execute { channel ->
            try {
                channel.txSelect()
                stockEntity.processed = false
                stockEntity.createdAt = Instant.now()
                val savedEntity = stockRepository.save(stockEntity)

                logger.info { "stock saved : $stockEntity" }

                rabbitTemplate.convertAndSend("transactionQueue", savedEntity)

                if ("fail".equals(testCase, ignoreCase = true)) {
                    throw RuntimeException("트랜잭션 작업 중 에러")
                }
                channel.txCommit()
                logger.info { "트랜잭션 정상 처리!!!!!!!!" }
            } catch (e: Exception) {
                logger.info { "트랜잭션 실패 ${e.message}" }
                channel.txRollback()
                throw RuntimeException("트랜잭션 롤백 완료 $e")
            }
//                finally {
//                    channel.close()
//                }
        }
    }
}