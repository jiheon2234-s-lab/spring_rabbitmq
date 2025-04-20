package com.hellj.rabbit.step2

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class WorkQueueConsumer {

    fun workQueueTask(message: String) {
        val messageParts = message.split("|").filter { it.isNotEmpty() }.toTypedArray()
        val originMessage = messageParts[0]
        val duration = messageParts[1].toInt()

        logger.info { "# Received: $originMessage (duration: $duration ms)" }

        try {
            val seconds = duration / 1000
            repeat(seconds) {
                Thread.sleep(1000)
                print(".")
            }
            println()
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        logger.info { "[#] Completed $originMessage" }
    }

}

