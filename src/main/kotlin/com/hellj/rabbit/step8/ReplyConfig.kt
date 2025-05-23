package com.hellj.rabbit.step8

import org.springframework.context.annotation.Bean
import org.springframework.retry.backoff.FixedBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate

//@Configuration
// application.yml에서 설정하도록 변경
class ReplyConfig {

    @Bean
    fun retryTemplate(): RetryTemplate {
        val simpleRetryPolicy = SimpleRetryPolicy().apply { maxAttempts = 3 }
        val fixedBackOffPolicy = FixedBackOffPolicy().apply { backOffPeriod = 1000 }

        return RetryTemplate().apply {
            this.setRetryPolicy(simpleRetryPolicy)
            this.setBackOffPolicy(fixedBackOffPolicy)
        }
    }
}