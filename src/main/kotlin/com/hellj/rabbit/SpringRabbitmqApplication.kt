package com.hellj.rabbit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.hellj.rabbit.step8"])
class SpringRabbitmqApplication

fun main(args: Array<String>) {
    runApplication<SpringRabbitmqApplication>(*args)
}
