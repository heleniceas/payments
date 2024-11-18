package br.com.caju.payments.application

import br.com.caju.payments.configuration.InsufficientBalanceExceptionForBenefits
import br.com.caju.payments.controller.request.TransferCreditCardRequest
import br.com.caju.payments.domain.TransferCreditCard
import br.com.caju.payments.domain.balance.impl.CashBalance
import br.com.caju.payments.domain.balance.impl.MealBalance
import br.com.caju.payments.domain.factory.BalanceFactory
import br.com.caju.payments.entity.AccountEntity
import br.com.caju.payments.entity.EstablishmentEntity
import br.com.caju.payments.repository.AccountRepository
import br.com.caju.payments.repository.EstablishmentRepository
import br.com.caju.payments.service.TransactionService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class TransferApplicationTest {

    @InjectMockKs
    lateinit var transferApplication: TransferApplication

    @RelaxedMockK
    lateinit var establishmentRepository: EstablishmentRepository

    @RelaxedMockK
    lateinit var transactionService: TransactionService

    @RelaxedMockK
    lateinit var factory: BalanceFactory


    @Test
    fun `should successfully process withdrawal with sufficient balance`() {
        val transferCreditCardRequest = mockk<TransferCreditCardRequest>()
        val accountRepository = mockk<AccountRepository>()
        val balanceFactory = CashBalance(accountRepository = accountRepository)
        val account = mockk<AccountEntity>()
        val amountBalance = 100.0
        val establishmentEntity = createEstablishmentEntity()
        val transferCreditCard = mockk<TransferCreditCard>()

        every { transferCreditCardRequest.account } returns "12345"
        every { transferCreditCardRequest.merchant } returns "MerchantName"
        every { transferCreditCardRequest.mcc } returns "5811"
        every { transferCreditCardRequest.totalAmount } returns 10.0
        every { establishmentRepository.findByName(any()) } returns establishmentEntity
        every { balanceFactory.getAccount(any()) } returns account
        every { balanceFactory.checkBalanceByType(account.number) } returns amountBalance
        every { transactionService.processTransaction(balanceFactory, account, transferCreditCard) } returns 50.0

        transferApplication.withdrawal(transferCreditCardRequest)

        verify(exactly = 1) {
            transactionService.processTransaction(
                balanceStrategy = any<CashBalance>(),
                account = any<AccountEntity>(),
                transferCreditCard = any<TransferCreditCard>()
            )

        }
    }

    @Test
    fun `should successfully process withdrawal with sufficient balance  meal and cash`() {
        val transferCreditCardRequest = mockk<TransferCreditCardRequest>()
        val accountRepository = mockk<AccountRepository>()
        val cashBalance = CashBalance(accountRepository = accountRepository)
        val mealBalance = MealBalance(accountRepository = accountRepository)
        val account = mockk<AccountEntity>()
        val amountBalance = 100.0
        val establishmentEntity = createEstablishmentEntity()
        val transferCreditCard = mockk<TransferCreditCard>()

        every { transferCreditCardRequest.account } returns "12345"
        every { transferCreditCardRequest.merchant } returns "MerchantName"
        every { transferCreditCardRequest.mcc } returns "5811"
        every { transferCreditCardRequest.totalAmount } returns 50.0
        every { establishmentRepository.findByName(any()) } returns establishmentEntity
        every { cashBalance.getAccount(any()) } returns account
        every { cashBalance.checkBalanceByType(account.number) } returns amountBalance


        every {
            transactionService.processTransaction(
                mealBalance,
                account,
                transferCreditCard
            )
        } throws InsufficientBalanceExceptionForBenefits()
        every { transactionService.processTransaction(cashBalance, account, transferCreditCard) } returns 150.0

        transferApplication.withdrawal(transferCreditCardRequest)


        verify(exactly = 1) {
            transactionService.processTransaction(
                balanceStrategy = any<CashBalance>(),
                account = any(),
                transferCreditCard = any<TransferCreditCard>()
            )

            transactionService.processTransaction(
                balanceStrategy = any<MealBalance>(),
                account = any(),
                transferCreditCard = any<TransferCreditCard>()
            )

        }
    }

    private fun createEstablishmentEntity(): EstablishmentEntity {
        return EstablishmentEntity(
            id = UUID.randomUUID(),
            name = "IFOOD",
            type = "MEAL"
        )
    }

}
