package com.hellj.rabbit.step5

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/logs")
class LogController(
    private val exceptionHandler: CustomExceptionHandler
) {

    @GetMapping("/error")
    fun errorAPI(): ResponseEntity<String> {
        val nullStr: String? = null
        try {
            nullStr!!.toByteArray()
        } catch (e: Exception) {
            exceptionHandler.handleException(e)
        }
        return ResponseEntity.ok("hanlde controller NPE")
    }

    @GetMapping("/warn")
    fun warnAPI(): ResponseEntity<String> {
        try {
            throw IllegalArgumentException("invalid argument")
        } catch (e: Exception) {
            exceptionHandler.handleException(e)
        }
        return ResponseEntity.ok("handle IllegalArgument Exception")
    }

    @PostMapping("/info")
    fun infoApi(@RequestBody message: String): ResponseEntity<String> {
        exceptionHandler.handleMessage(message)
        return ResponseEntity.ok("Send info Log")
    }
}