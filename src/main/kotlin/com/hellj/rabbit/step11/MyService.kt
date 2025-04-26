package com.hellj.rabbit.step11

import com.hellj.rabbit.step11.entity.MyEntity
import com.hellj.rabbit.step11.entity.MyEntityRepository
import com.hellj.rabbit.step11.entity.MyEventEntity
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class MyService(
    private val myEntityRepository: MyEntityRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional
    fun createEntity(message: String) {
        val savedMyEntity = myEntityRepository.save(
            MyEntity(message)
        )
        val myEventBeforeSaved = MyEventEntity(savedMyEntity.message)
        applicationEventPublisher.publishEvent(myEventBeforeSaved)
        if (message.equals("t_fails", ignoreCase = true)) {
            throw RuntimeException("transaction_fail!!!!!")
        }
    }
}