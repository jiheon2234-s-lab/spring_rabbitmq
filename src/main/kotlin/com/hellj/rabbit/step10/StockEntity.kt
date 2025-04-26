package com.hellj.rabbit.step10

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant

@Entity
class StockEntity(
    var userId: String,
    var stock: Int,
    var processed: Boolean,

    var createdAt: Instant?,
    var updatedAt: Instant?
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}