package br.com.caju.payments.application

import br.com.caju.payments.commons.logger
import br.com.caju.payments.configuration.InsufficientBalanceExceptionForBenefits
import br.com.caju.payments.controller.request.TransferCreditCardRequest
import br.com.caju.payments.controller.request.toTransferCreditCard
import br.com.caju.payments.domain.Establishment
import br.com.caju.payments.domain.balance.BalanceType
import br.com.caju.payments.domain.factory.BalanceFactory
import br.com.caju.payments.entity.toEstablishment
import br.com.caju.payments.repository.EstablishmentRepository
import br.com.caju.payments.service.TransactionService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransferApplication(
    private val transactionService: TransactionService,
    private val factory: BalanceFactory,
    private val establishmentRepository: EstablishmentRepository,
) {

    private val logger = logger()

    @Transactional(timeout = 1)
    fun withdrawal(transferCreditCardRequest: TransferCreditCardRequest) {
        val establishment = establishmentRepository.findByName(transferCreditCardRequest.merchant)
        val balanceType = getBalanceType(establishment?.toEstablishment(), transferCreditCardRequest.mcc)
        val balanceFactory = factory.getType(balanceType)
        val account = balanceFactory.getAccount(transferCreditCardRequest.account)
        val amountBalance = balanceFactory.checkBalanceByType(account.number)
        val transferCreditCard =
            transferCreditCardRequest.toTransferCreditCard(balanceFactory.getType(), establishment?.toEstablishment(), amountBalance)

        logger.info("Current balance: ${balanceFactory.getAccount(account.number)}")
        val newBalance = try {
            transactionService.processTransaction(
                balanceStrategy = balanceFactory,
                account = account,
                transferCreditCard
            )
        } catch (ex: InsufficientBalanceExceptionForBenefits) {
            logger.warn("Insufficient balance for benefits in account ${account.number}, trying with cash balance.")
            transactionService.processTransaction(
                balanceStrategy = factory.getType(BalanceType.CASH.name),
                account = account,
                transferCreditCard
            )
        }
        logger.info("Current balance: $newBalance Type $balanceType account: ${account.number}  ")

    }

    private fun getBalanceType(establishment: Establishment?, mcc: String): String {
        return establishment?.type?.name ?: BalanceType.fromMcc(mcc)
    }


}



