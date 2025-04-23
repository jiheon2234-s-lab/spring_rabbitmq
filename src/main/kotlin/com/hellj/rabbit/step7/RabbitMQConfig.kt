package com.hellj.rabbit.step7

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        const val ORDER_COMPLETED_QUEUE = "order_completed_queue"
        const val ORDER_EXCHANGE = "order_completed_exchange"
        const val DLQ = "deadLetterQueue"
        const val DLX = "deadLetterExchange"
    }

    @Bean
    fun orderExchange(): TopicExchange {
        return TopicExchange(ORDER_EXCHANGE)
    }

    @Bean
    fun deadLetterExchange(): TopicExchange {
        return TopicExchange(DLX)
    }

    @Bean
    fun orderQueue(): Queue {
        return QueueBuilder.durable(ORDER_COMPLETED_QUEUE)
            .withArgument("x-dead-letter-exchange", DLX)
            .withArgument("x-dead-letter-routing-key", DLQ)
            .ttl(5000)
            .build()
    }

    @Bean
    fun deadLetterQueue(): Queue {
        return Queue(DLQ)
    }

    @Bean
    fun orderCompletedBinding(orderQueue: Queue, orderExchange: TopicExchange): Binding {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order.completed.#")
    }

    @Bean
    fun deadLetterBinding(deadLetterQueue: Queue, deadLetterExchange: TopicExchange): Binding {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DLQ)
    }
}