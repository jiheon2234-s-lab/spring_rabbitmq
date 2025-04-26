package com.hellj.rabbit.step4

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/news/api")
class NewsRestController(
    private val newsPublisher: NewsPublisher
) {

    @PostMapping("/publish")
    fun publishNews(@RequestParam newsType: String): ResponseEntity<String> {
        val result = newsPublisher.publishAPI(newsType)
        return ResponseEntity.ok("# Message published to RabbitMQ: $result")
    }
}