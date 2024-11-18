package br.com.caju.payments.entity

import br.com.caju.payments.domain.Establishment
import br.com.caju.payments.domain.balance.BalanceType
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "establishment", indexes = [Index(name = "establishment_name_index", columnList = "name", unique = true)])
data class EstablishmentEntity(
    @Id
    @Column(nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = true, length = 20)
    val type: String? = null
)

fun EstablishmentEntity.toEstablishment(): Establishment {
    return Establishment(
            id= id,
            name = this.name,
            type = this.type?.let { BalanceType.valueOf(it) }
        )
}