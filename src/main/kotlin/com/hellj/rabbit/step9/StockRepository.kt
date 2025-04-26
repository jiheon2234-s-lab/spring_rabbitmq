package com.hellj.rabbit.step9

import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<StockEntity, Long> {
}