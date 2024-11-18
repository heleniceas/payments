package br.com.caju.payments.domain.factory

import br.com.caju.payments.domain.balance.*
import br.com.caju.payments.domain.balance.impl.CashBalance
import br.com.caju.payments.domain.balance.impl.FoodBalance
import br.com.caju.payments.domain.balance.impl.MealBalance
import br.com.caju.payments.repository.AccountRepository
import org.springframework.stereotype.Component

@Component
class BalanceFactory(
    private val accountRepository: AccountRepository,
) {

    fun getType(tipo: String): BalanceStrategy {
        return when (tipo.uppercase()) {
            BalanceType.FOOD.name -> FoodBalance(accountRepository)
            BalanceType.MEAL.name -> MealBalance(accountRepository)
            BalanceType.CASH.name-> CashBalance(accountRepository)
            else -> throw IllegalArgumentException("Tipo de saldo desconhecido: $tipo")
        }
    }

}