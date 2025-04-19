package com.hellj.rabbit.step1

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        const val QUEUE_NAME = "helloqueue"
    }


    /**
     * 사용할 큐
     * 첫 파라미터는 큐 이름, 두 번째 파라미터는 휘발성 여부를 나타낸다.
     * 두 번째 파라미터가 false일 경우 서버가 종료되면 큐가 사라진다.
     */
    @Bean
    fun queue(): Queue {
        return Queue(QUEUE_NAME, false);
    }

    /**
     * RabbitMQ를 사용하기 위한 템플릿.
     */
    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        return RabbitTemplate(connectionFactory)
    }

    @Bean
    fun container(
        connectionFactory: ConnectionFactory,
        listenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer {
//        val container = SimpleMessageListenerContainer()
//        container.connectionFactory = connectionFactory
//        container.setQueueNames(QUEUE_NAME)
//        container.setMessageListener(listenerAdapter)
//        return container
        return SimpleMessageListenerContainer().apply {
            this.connectionFactory = connectionFactory
            setQueueNames(QUEUE_NAME)
            setMessageListener(listenerAdapter)
        }
    }

    /**
     * 수신될 메시지를 처리할 리스너 어뎁터
     * receiver 객체의 "receiveMessage"가 메시지를 처리
     */
    @Bean
    fun listenerAdapter(receiver: Receiver): MessageListenerAdapter {
        return MessageListenerAdapter(receiver, "receiveMessage")
    }
}