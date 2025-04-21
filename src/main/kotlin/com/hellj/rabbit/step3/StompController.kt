package com.hellj.rabbit.step3

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

private val logger = KotlinLogging.logger {}

@Controller
class StompController(
    private val messageTemplate: SimpMessagingTemplate
) {
    @MessageMapping("/send")
    fun sendMessage(notificationMessage: NotificationMessage) {
        // 수신된 메시지를 브로드캐스팅
        logger.info { "[#] message: $notificationMessage.message" }
        messageTemplate.convertAndSend("/topic/notifications", notificationMessage.message)
    }
}