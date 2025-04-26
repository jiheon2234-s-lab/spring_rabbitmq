package com.hellj.rabbit.step1

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}


@Component
class Receiver {

    fun receiveMessage(message: String) {
        logger.info { "[#] Received: $message" }
    }
}