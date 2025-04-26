package com.hellj.rabbit.step11

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MyController(
    private val myService: MyService
) {

    @GetMapping("custom")
    fun myTest(@RequestBody message: String): String {
        myService.createEntity(message)
        return "Hello!!!"
    }
}