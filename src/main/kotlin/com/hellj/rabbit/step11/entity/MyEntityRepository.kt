package com.hellj.rabbit.step11.entity

import org.springframework.data.jpa.repository.JpaRepository

interface MyEntityRepository : JpaRepository<MyEntity, Long> {
}