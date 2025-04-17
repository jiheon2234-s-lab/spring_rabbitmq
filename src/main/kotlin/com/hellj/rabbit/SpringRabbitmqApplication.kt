package com.hellj.rabbit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringRabbitmqApplication

fun main(args: Array<String>) {
    runApplication<SpringRabbitmqApplication>(*args)
}
