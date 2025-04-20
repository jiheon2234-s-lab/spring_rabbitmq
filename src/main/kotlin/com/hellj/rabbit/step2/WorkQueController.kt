package com.hellj.rabbit.step2

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class WorkQueController(
    private val workQueueProducer: WorkQueueProducer
) {

    @PostMapping("/workqueue")
    fun workQueue(@RequestParam message: String, @RequestParam duration: Int): String {
        workQueueProducer.sendWorkQueue(message, duration)
        return "Work queue sent = $message ($duration)"
    }
}