package com.hellj.rabbit.step4

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

private val logger = KotlinLogging.logger {}

@Controller
class NewsController(
    private val newsPublisher: NewsPublisher
) {

    @MessageMapping("/subscribe")
    fun handleSubscribe(@Header("newsType") newsType: String) {
        logger.info { "[#] newsType: $newsType" }
        val newMessage = newsPublisher.publish(newsType)
        logger.info { "# newsMessage: $newMessage" }
    }

}