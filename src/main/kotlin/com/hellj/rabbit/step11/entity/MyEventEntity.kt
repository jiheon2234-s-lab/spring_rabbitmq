package com.hellj.rabbit.step11.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * 테이블에 저장될 이벤트 객체
 */
@Entity
class MyEventEntity(
    val message: String // MyEntity의 메시지
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}