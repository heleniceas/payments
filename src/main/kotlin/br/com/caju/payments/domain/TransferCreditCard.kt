package br.com.caju.payments.domain

import br.com.caju.payments.domain.balance.BalanceType
import java.time.LocalDate

data class TransferCreditCard(
    val account: String,
    val totalAmount: Double,
    var balance: Double,
    val mcc: String,
    val merchant: String,
    val type: BalanceType,
    var establishment: Establishment
) {
    val idempotenceKey

        get() = (this.account +
                LocalDate.now() +
                totalAmount+
                mcc +
                establishment.name)
                    .replace(Regex("[^A-Za-z0-9]"), "")

}