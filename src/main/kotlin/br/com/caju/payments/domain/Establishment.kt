package br.com.caju.payments.domain

import br.com.caju.payments.domain.balance.BalanceType
import java.util.UUID

data class Establishment(
    val id: UUID,
    val name: String,
    val type: BalanceType?
)