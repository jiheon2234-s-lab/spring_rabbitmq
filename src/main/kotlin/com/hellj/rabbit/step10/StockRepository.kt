package com.hellj.rabbit.step10

import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<StockEntity, Long> {
}