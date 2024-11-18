package br.com.caju.payments.entity

import br.com.caju.payments.domain.balance.BalanceType
import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "transaction")
data class TransactionEntity(
    @Id
    @Column(nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val amount: Double,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 15)
    val type: BalanceType,

    @Column(name = "occurred_in", nullable = false)
    val occurredIn: LocalDate,

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    val account: AccountEntity,

    @ManyToOne
    @JoinColumn(name = "establishment_id", nullable = true)
    val establishment: EstablishmentEntity,

    @Column(name = "idempotency_key", nullable = false, length = 50)
    val idempotencyKey: String,
)