package br.com.caju.payments.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID


@Entity
@Table(name = "account")
data class AccountEntity(
    @Id
    @Column(nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 20)
    val number: String,

    @Column(name = "cash_amount", nullable = false)
    val cashAmount: Double,

    @Column(name = "meal_amount", nullable = false)
    val mealAmount: Double,

    @Column(name = "food_amount", nullable = false)
    val foodAmount: Double,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)