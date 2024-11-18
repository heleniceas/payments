package br.com.caju.payments.service

import br.com.caju.payments.domain.Establishment
import br.com.caju.payments.domain.TransferCreditCard
import br.com.caju.payments.domain.balance.BalanceType
import br.com.caju.payments.domain.balance.impl.FoodBalance
import br.com.caju.payments.entity.AccountEntity
import br.com.caju.payments.entity.EstablishmentEntity
import br.com.caju.payments.repository.AccountRepository
import br.com.caju.payments.repository.EstablishmentRepository
import br.com.caju.payments.repository.TransactionRepository
import io.mockk.*
import org.junit.jupiter.api.Test
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
class TransactionServiceTest {

    @RelaxedMockK
    lateinit var transactionRepository: TransactionRepository

    @RelaxedMockK
    lateinit var establishmentRepository: EstablishmentRepository

    @RelaxedMockK
    lateinit var calculateService: CalculateService


    @InjectMockKs
    lateinit var transactionService: TransactionService

    @Test
    fun `must successfully process a transaction with a registered establishment`() {

        val accountRepository = mockk<AccountRepository>()
        val account = createAccountEntity()
        val establishmentEntity = createEstablishmentEntity()
        val transferCreditCard = createTransferCreditCard()
        val balanceStrategy = FoodBalance(accountRepository)

        every { accountRepository.findByNumber(any()) } returns account
        every { balanceStrategy.getAccount(any()) } returns account
        every { balanceStrategy.checkBalanceByType(any()) } returns 50.0
        every { balanceStrategy.pay(any(), any()) } returns account
        every { accountRepository.findByNumber(any()) } returns account
        every { accountRepository.save(any()) } returns account
        every { establishmentRepository.findById(any()) } returns Optional.of(establishmentEntity)


        transactionService.processTransaction(balanceStrategy, account, transferCreditCard)

        verify(exactly = 1) { transactionRepository.save(any()) }
        verify(exactly = 0) { establishmentRepository.save(any()) }

    }

    @Test
    fun `must successfully process a transaction with NOT registered establishment`() {
        val accountRepository = mockk<AccountRepository>()
        val account = createAccountEntity()
        val establishmentEntity = createEstablishmentEntity()
        val transferCreditCard = createTransferCreditCard()
        val balanceStrategy = FoodBalance(accountRepository)

        every { accountRepository.findByNumber(any()) } returns account
        every { balanceStrategy.getAccount(any()) } returns account
        every { balanceStrategy.checkBalanceByType(any()) } returns 50.0
        every { balanceStrategy.pay(any(), any()) } returns account
        every { accountRepository.findByNumber(any()) } returns account
        every { accountRepository.save(any()) } returns account
        every { establishmentRepository.findById(any()) } returns Optional.empty()
        every { establishmentRepository.save(any()) } returns establishmentEntity

        transactionService.processTransaction(balanceStrategy, account, transferCreditCard)

        verify(exactly = 1) { transactionRepository.save(any()) }
        verify(exactly = 1) { establishmentRepository.save(any()) }

    }

    private fun createAccountEntity(): AccountEntity {
        return AccountEntity(
            id = UUID.randomUUID(),
            number = "12345",
            cashAmount = 10.0,
            mealAmount = 200.00,
            foodAmount = 300.00,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    private fun createEstablishmentEntity(): EstablishmentEntity {
        return EstablishmentEntity(
            id = UUID.randomUUID(),
            name = "IFOOD",
            type = "MEAL"
        )
    }

    private fun createTransferCreditCard(): TransferCreditCard {
        return TransferCreditCard(
            account = "12345",
            totalAmount = 10.0,
            balance = 200.0,
            mcc = "5411",
            merchant = "IFOOD",
            type = BalanceType.FOOD,
            establishment = Establishment(UUID.randomUUID(), name = "IFOOD", type = BalanceType.FOOD)
        )
    }


}