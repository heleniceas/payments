package br.com.caju.payments.service

import br.com.caju.payments.configuration.InsufficientBalanceException
import br.com.caju.payments.configuration.InsufficientBalanceExceptionForBenefits
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class CalculateServiceTest {

    @InjectMockKs
    lateinit var calculateService: CalculateService

    @Test
    fun `should return the correct balance when balance is sufficient`() {
        val balance = 200.0
        val amount = 100.0
        val type = "CASH"

        val result = calculateService.calculateAmount(balance, amount, type)

        assertEquals(100.0, result)
    }

    @Test
    fun `should throw InsufficientBalanceException when balance is insufficient and type is CASH`() {
        val balance = 50.0
        val amount = 100.0
        val type = "CASH"

        assertThrows<InsufficientBalanceException> {
            calculateService.calculateAmount(balance, amount, type)
        }
    }

    @Test
    fun `should throw InsufficientBalanceExceptionForBenefits when balance is insufficient and type is not CASH`() {
        val balance = 50.0
        val amount = 100.0
        val type = "BENEFIT"

        assertThrows<InsufficientBalanceExceptionForBenefits> {
            calculateService.calculateAmount(balance, amount, type)
        }
    }

}