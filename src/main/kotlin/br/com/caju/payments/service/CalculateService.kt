package br.com.caju.payments.service

import br.com.caju.payments.configuration.InsufficientBalanceException
import br.com.caju.payments.configuration.InsufficientBalanceExceptionForBenefits
import br.com.caju.payments.configuration.TransactionFailureException
import br.com.caju.payments.domain.balance.BalanceType
import org.springframework.stereotype.Component

@Component
class CalculateService {
    fun calculateAmount(balance: Double, amount: Double, type: String): Double {
        return when {
            balance >= amount -> {
                balance - amount
            }

            balance < amount && type == BalanceType.CASH.name -> {
                throw InsufficientBalanceException()
            }

            balance < amount -> {
                throw InsufficientBalanceExceptionForBenefits()
            }

            else -> {
                throw TransactionFailureException()
            }
        }
    }
}