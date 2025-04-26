package com.hellj.rabbit.step11.entity

import org.springframework.data.jpa.repository.JpaRepository

interface MyEventEntityRepository : JpaRepository<MyEventEntity, Long> {
}