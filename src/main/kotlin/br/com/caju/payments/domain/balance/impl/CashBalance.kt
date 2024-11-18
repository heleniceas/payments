package br.com.caju.payments.domain.balance.impl

import br.com.caju.payments.domain.balance.BalanceStrategy
import br.com.caju.payments.domain.balance.BalanceType
import br.com.caju.payments.entity.AccountEntity
import br.com.caju.payments.repository.AccountRepository

class CashBalance(
    val accountRepository: AccountRepository
) : BalanceStrategy {


    override fun getAccount(account: String): AccountEntity {
        return accountRepository.findByNumber(account)
            ?: throw NoSuchElementException("Conta não encontrada: $account")
    }

    override fun checkBalanceByType(account: String): Double {
        return getAccount(account).cashAmount
    }

    override fun pay(account: String, newBalance: Double): AccountEntity {
        getAccount(account).let {
            return accountRepository.save(it.copy(cashAmount = newBalance))
        }
    }

    override fun getType() = BalanceType.CASH
}
