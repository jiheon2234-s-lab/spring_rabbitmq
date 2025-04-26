package com.hellj.rabbit.step10

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/message")
class TransactionController(
    private val messageProducer: MessageProducer,
) {

    @PostMapping
    fun publishMessage(
        @RequestBody stockEntity: StockEntity,
        @RequestParam testCase: Boolean
    ): ResponseEntity<String?> {
        logger.info { "Publisher Confirms Send message : $stockEntity" }

        try {
            messageProducer.sendMessage(stockEntity, testCase)
            return ResponseEntity.ok("Publisher Confirms sent successfully")
        } catch (e: RuntimeException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Publisher Confirms FAIL!!!!! : ${e.message}")
        }
    }
}