package com.hellj.rabbit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

const val CURRENT_PACKAGE_NAME = "com.hellj.rabbit.step10"

@SpringBootApplication(scanBasePackages = [CURRENT_PACKAGE_NAME])
@EnableJpaRepositories(basePackages = [CURRENT_PACKAGE_NAME])
@EntityScan(basePackages = [CURRENT_PACKAGE_NAME])
class SpringRabbitmqApplication

fun main(args: Array<String>) {
    runApplication<SpringRabbitmqApplication>(*args)
}
