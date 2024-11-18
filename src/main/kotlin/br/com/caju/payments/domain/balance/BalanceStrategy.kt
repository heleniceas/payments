package br.com.caju.payments.domain.balance

import br.com.caju.payments.entity.AccountEntity

interface BalanceStrategy {
    fun getAccount(account: String): AccountEntity
    fun checkBalanceByType(account: String): Double
    fun pay(account: String, newBalance: Double): AccountEntity
    fun getType() : BalanceType
}