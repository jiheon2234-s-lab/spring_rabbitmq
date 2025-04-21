package com.hellj.rabbit.step4

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class NewsSubscriber(
    private val messagingTemplate: SimpMessagingTemplate
) {

    @RabbitListener(queues = [RabbitMQConfig.JAVA_QUEUE])
    fun javaNews(message: String) {
        messagingTemplate.convertAndSend("/topic/java", message)
    }

    @RabbitListener(queues = [RabbitMQConfig.SPRING_QUEUE])
    fun springNews(message: String) {
        messagingTemplate.convertAndSend("/topic/spring", message)
    }

    @RabbitListener(queues = [RabbitMQConfig.VUE_QUEUE])
    fun vueNews(message: String) {
        messagingTemplate.convertAndSend("/topic/vue", message)
    }

}