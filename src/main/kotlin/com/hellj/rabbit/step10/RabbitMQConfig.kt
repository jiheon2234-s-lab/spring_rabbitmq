package com.hellj.rabbit.step10

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val logger = KotlinLogging.logger {}

@Configuration
class RabbitMQConfig {

    companion object {
        const val QUEUE_NAME: String = "transactionQueue"
        const val DLQ_NAME: String = "deadLetterQueue"

        const val EXCHANGE_NAME: String = "transactionExchange"
        const val ROUTING_KEY: String = "transactionRoutingKey"
    }

    @Bean
    fun transactionQueue(): Queue {
        return QueueBuilder.durable(QUEUE_NAME)
            .deadLetterExchange("")
            .deadLetterRoutingKey(DLQ_NAME)
            .build()
    }

    @Bean
    fun deadLetterQueue(): Queue {
        return Queue(DLQ_NAME)
    }

    @Bean
    fun transactionExchange(): DirectExchange {
        return DirectExchange(EXCHANGE_NAME)
    }

    @Bean
    fun transactionBinding(
        transactionQueue: Queue,
        transactionExchange: DirectExchange
    ): Binding {
        return BindingBuilder.bind(transactionQueue).to(transactionExchange).with(ROUTING_KEY)
    }

    @Bean
    fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    // RabbitTemplate 설정, ReturnCallback 활성화 등록, ConfirmCallback 설정
    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory, messageConverter: MessageConverter): RabbitTemplate {
        return RabbitTemplate(connectionFactory).apply {
            this.messageConverter = messageConverter
            setMandatory(true)

            setConfirmCallback { correlationData, ack, cause ->
                if (ack) {
                    logger.info { "### [Message confirmed]: ${correlationData?.id ?: "null"}" }
                } else {
                    logger.info { "### [Message not confirmed]: ${correlationData?.id ?: "null"}" }

                    //실패인 경우 추가 처리 로직
                }

            }

            setReturnsCallback { returned ->

                logger.info { "Return Message ${returned.message.body}" }
                logger.info { "Exchange : ${returned.exchange}" }
                logger.info { "RoutingKey : ${returned.routingKey}" }

            }
        }
    }

    @Bean
    fun rabbitListenerContainerFactory(
        connectionFactory: ConnectionFactory,
        messageConverter: MessageConverter
    ): SimpleRabbitListenerContainerFactory {
        return SimpleRabbitListenerContainerFactory().apply {
            setConnectionFactory(connectionFactory)
            setMessageConverter(messageConverter)
            setAcknowledgeMode(AcknowledgeMode.MANUAL)
        }
    }

}