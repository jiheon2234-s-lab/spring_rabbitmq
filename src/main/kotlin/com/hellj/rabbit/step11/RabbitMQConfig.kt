package com.hellj.rabbit.step11

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {

    companion object {
        const val QUEUE_NAME = "MY_QUEUE"
        const val EXCHANGE_NAME = "MY_EXCHANGE"
        const val ROUTING_KEY = "MY_ROUTING_KEY"
    }


    @Bean
    fun queue(): Queue {
        return Queue(QUEUE_NAME)
    }

    @Bean
    fun directExchange(): DirectExchange {
        return DirectExchange(EXCHANGE_NAME)
    }

    @Bean
    fun binding(queue: Queue, directExchange: DirectExchange): Binding {
        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY)
    }

    @Bean
    fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

}