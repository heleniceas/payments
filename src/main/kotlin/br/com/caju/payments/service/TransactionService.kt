package br.com.caju.payments.service

import br.com.caju.payments.domain.TransferCreditCard
import br.com.caju.payments.domain.balance.BalanceStrategy
import br.com.caju.payments.entity.AccountEntity
import br.com.caju.payments.entity.EstablishmentEntity
import br.com.caju.payments.entity.TransactionEntity
import br.com.caju.payments.repository.EstablishmentRepository
import br.com.caju.payments.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class TransactionService(
    val transactionRepository: TransactionRepository,
    val establishmentRepository: EstablishmentRepository,
    val calculateService: CalculateService
) {
    fun processTransaction(
        balanceStrategy: BalanceStrategy,
        account: AccountEntity,
        transferCreditCard: TransferCreditCard,
    ): Double {

        val newBalance = calculateService.calculateAmount(
            balance = balanceStrategy.checkBalanceByType(account.number),
            amount = transferCreditCard.totalAmount,
            type = balanceStrategy.getType().name
        )
        val accountEntity = balanceStrategy.pay(account = account.number, newBalance = newBalance)

        createTransaction(
            transferCreditCard = transferCreditCard,
            accountEntity = accountEntity
        )
        return newBalance
    }


    private fun createTransaction(
        transferCreditCard: TransferCreditCard,
        accountEntity: AccountEntity
    ) {
        val establishmentEntity = establishmentRepository.findById(transferCreditCard.establishment.id)
            .orElseGet {
                establishmentRepository.save(
                    EstablishmentEntity(
                        UUID.randomUUID(),
                        name = transferCreditCard.merchant,
                        type = transferCreditCard.establishment.type?.name
                    )
                )
            }

        val transaction = TransactionEntity(
            amount = transferCreditCard.totalAmount,
            type = transferCreditCard.type,
            occurredIn = LocalDate.now(),
            account = accountEntity,
            establishment = establishmentEntity,
            idempotencyKey = transferCreditCard.idempotenceKey
        )
        transactionRepository.save(transaction)
    }


}






