package com.hellj.rabbit.step5

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {

    companion object {
        const val ERROR_QUEUE = "error_queue"
        const val WARN_QUEUE = "warn_queue"
        const val INFO_QUEUE = "info_queue"

//        const val DIRECT_EXCHANGE = "direct_exchange"
        
        const val ALL_LOG_QUE = "all_log_queue"
        const val TOPIC_EXCHANGE = "topic_exchange"
    }

    @Bean
    fun topicExchange(): TopicExchange {
        return TopicExchange(TOPIC_EXCHANGE)
    }

//    @Bean
//    fun directExchange(): DirectExchange {
//        return DirectExchange(DIRECT_EXCHANGE)
//    }

    @Bean
    fun errorQueue(): Queue {
        return Queue(ERROR_QUEUE, false)
    }

    @Bean
    fun warnQueue(): Queue {
        return Queue(WARN_QUEUE, false)
    }

    @Bean
    fun infoQueue(): Queue {
        return Queue(INFO_QUEUE, false)
    }

    @Bean
    fun allLogQueue(): Queue {
        return Queue(ALL_LOG_QUE, false)
    }

    @Bean
    fun errorBinding(topicExchange: TopicExchange): Binding {
        return BindingBuilder.bind(errorQueue()).to(topicExchange).with("log.error")
    }

    @Bean
    fun warnBinding(topicExchange: TopicExchange): Binding {
        return BindingBuilder.bind(warnQueue()).to(topicExchange).with("log.warn")
    }

    @Bean
    fun infoBinding(topicExchange: TopicExchange): Binding {
        return BindingBuilder.bind(infoQueue()).to(topicExchange).with("log.info")
    }

    @Bean
    fun allLogBinding(topicExchange: TopicExchange): Binding {
        return BindingBuilder.bind(allLogQueue()).to(topicExchange).with("log.*")
    }
}