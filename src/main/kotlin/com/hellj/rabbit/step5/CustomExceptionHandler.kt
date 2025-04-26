package com.hellj.rabbit.step5

import org.springframework.stereotype.Component

@Component
class CustomExceptionHandler(
    private val logPublisher: LogPublisher
) {

    fun handleException(e: Exception) {
        val message = e.message

        val routingKey = when (e) {
            is NullPointerException -> "log.error"
            is IllegalArgumentException -> "log.warn"
            else -> "log.error"
        }

        logPublisher.publish(routingKey, "Exception occurs.. $message")
    }

    fun handleMessage(message: String) {
        val routingKey = "info"
        logPublisher.publish(routingKey, "Info Log: $message")
    }

}