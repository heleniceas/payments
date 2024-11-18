package br.com.caju.payments.controller.request

import br.com.caju.payments.domain.Establishment
import br.com.caju.payments.domain.TransferCreditCard
import br.com.caju.payments.domain.balance.BalanceType
import java.util.UUID

data class TransferCreditCardRequest(
    val account: String,
    val totalAmount: Double,
    val mcc: String,
    val merchant: String,
)

fun TransferCreditCardRequest.toTransferCreditCard(
    balanceType: BalanceType,
    establishment: Establishment? = null,
    balance: Double
): TransferCreditCard {

    return TransferCreditCard(
        account = this.account,
        totalAmount = this.totalAmount,
        mcc = this.mcc,
        merchant = this.merchant,
        type = balanceType,
        balance = balance,
        establishment = establishment ?: Establishment(
            id = UUID.randomUUID(),
            name = this.merchant,
            type = balanceType
        )
    )
}