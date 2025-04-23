package com.hellj.rabbit.step7

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order")
class OrderController(
    private val orderProducer: OrderProducer
) {

    @GetMapping
    fun sendOrderMessage(@RequestParam message: String): ResponseEntity<String> {
        orderProducer.sendShipping(message)
        return ResponseEntity.ok("Order Completed.... Message sent: $message")
    }
}