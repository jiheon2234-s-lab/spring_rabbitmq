package com.hellj.rabbit.step11

import com.hellj.rabbit.step11.entity.MyEventEntity
import com.hellj.rabbit.step11.entity.MyEventEntityRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

private val logger = KotlinLogging.logger {}

@Component
class CustomEventHandler(
    private val rabbitTemplate: RabbitTemplate,
    private val myEventRepository: MyEventEntityRepository
) {
    /**
     * 트랜잭션이 커밋되기 전, 이벤트를 DB에 저장한다. "메시지 전송이 실패한 경우"를 대비한다.
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun recordEvent(myEvent: MyEventEntity) {
        myEventRepository.save(myEvent)
        logger.info { "myEvent : $myEvent" }
    }


    /**
     * 트랜잭션이 성공한 경우에만 메시지를 보낸다.
     */
//    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun publishEvent(myEvent: MyEventEntity) {
        val routingKey =
            if (myEvent.message.equals(
                    "q_fail",
                    ignoreCase = true
                )
            ) "INVALID_ROUTING_KEY" else RabbitMQConfig.ROUTING_KEY
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, myEvent)
    }

}