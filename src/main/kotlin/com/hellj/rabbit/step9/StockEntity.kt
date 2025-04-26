package com.hellj.rabbit.step9

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.Instant

@Entity
class StockEntity(
    var userId: String,
    var stock: Int,
    var processed: Boolean,
    var createdAt: Instant,
    var updatedAt: Instant?,
) {

    @Id
    @GeneratedValue
    val id: Long? = null
}