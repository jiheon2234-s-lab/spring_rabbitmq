package com.hellj.rabbit.step8

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        const val ORDER_COMPLETED_QUEUE: String = "orderCompletedQueue"
        const val DLQ: String = "deadLetterQueue"
        const val ORDER_TOPIC_EXCHANGE: String = "orderExchange"
        const val ORDER_TOPIC_DLX: String = "deadLetterExchange"
        const val DEAD_LETTER_ROUTING_KEY: String = "dead.letter"
    }

    //원래큐
    @Bean
    fun orderExchange(): TopicExchange {
        return TopicExchange(ORDER_TOPIC_EXCHANGE)
    }

    @Bean
    fun deadLetterExchange(): TopicExchange {
        return TopicExchange(ORDER_TOPIC_DLX)
    }

    @Bean
    fun orderQueue(): Queue {
        return QueueBuilder.durable(ORDER_COMPLETED_QUEUE)
            .deadLetterExchange(ORDER_TOPIC_DLX)
            .deadLetterRoutingKey(DEAD_LETTER_ROUTING_KEY)
//            .withArgument("x-dead-letter-exchange", ORDER_TOPIC_DLX) // DLX 설정
//            .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY) // DLQ로 이동할 라우팅 키 설정
            .build()
    }

    @Bean
    fun deadLetterQueue(): Queue? {
        return QueueBuilder.durable(DLQ).build()
    }

    @Bean
    fun orderQueueBinding(orderQueue: Queue, orderExchange: TopicExchange): Binding {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order.completed.*")
    }

    @Bean
    fun deadLetterQueueBinding(deadLetterQueue: Queue, deadLetterExchange: TopicExchange): Binding? {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DEAD_LETTER_ROUTING_KEY)
    }
}