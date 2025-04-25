package com.hellj.rabbit.step9

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/message")
class TransactionController(
    private val messageProducer: MessageProducer
) {

    @PostMapping
    fun sendMessage(
        @RequestBody stockEntity: StockEntity,
        @RequestParam(required = false, defaultValue = "success") testCase: String
    ): ResponseEntity<String> {
        logger.info { "Send Message : $stockEntity" }

        try {
            messageProducer.sendMessage(stockEntity, testCase)
            return ResponseEntity.ok("Message sent Successfully")
        } catch (e: RuntimeException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Mq 트랜잭션 실패 : ${e.message}")
        }
    }
}