package br.com.caju.payments

import br.com.caju.payments.commons.logger
import br.com.caju.payments.domain.balance.BalanceType
import br.com.caju.payments.entity.AccountEntity
import br.com.caju.payments.entity.EstablishmentEntity
import br.com.caju.payments.repository.AccountRepository
import br.com.caju.payments.repository.EstablishmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentsApplication: CommandLineRunner {

	@Autowired
	lateinit var accountRepository: AccountRepository

	@Autowired
	lateinit var establishmentRepository: EstablishmentRepository

	private val logger = logger()

	override fun run(vararg args: String?) {
		logger.info("Creating data")
		createAccounts()
		createEstablishments()
	}


	private fun createEstablishments() {
		val establishments = listOf(
			EstablishmentEntity(
				name = "UBER EATS",
				type = BalanceType.FOOD.name
			),
			EstablishmentEntity(
				name = "UBER TRIP",
				type = null
			),
			EstablishmentEntity(
				name = "PAG*JoseDaSilva",
				type = BalanceType.MEAL.name
			),
			EstablishmentEntity(
				name = "PICPAY*BILHETEUNICO",
				type = null
			)
		)

		establishmentRepository.saveAll(establishments)
		logger.info("Establishments created: ${establishments.size}")
	}


	private fun createAccounts() {
		val accounts = listOf(
			AccountEntity(
				number = "1234567890",
				cashAmount = 1500.0,
				mealAmount = 200.0,
				foodAmount = 100.0
			),
			AccountEntity(
				number = "9876543210",
				cashAmount = 2500.5,
				mealAmount = 300.0,
				foodAmount = 150.0
			),
			AccountEntity(
				number = "1122334455",
				cashAmount = 1200.75,
				mealAmount = 180.0,
				foodAmount = 90.0
			),
			AccountEntity(
				number = "9988776655",
				cashAmount = 5000.0,
				mealAmount = 450.0,
				foodAmount = 200.0
			),
			AccountEntity(
				number = "2233445566",
				cashAmount = 3200.0,
				mealAmount = 400.0,
				foodAmount = 220.0
			)
		)

		accountRepository.saveAll(accounts)
		logger.info("Contas criadas: ${accounts.size}")
	}

}


fun main(args: Array<String>) {
	runApplication<PaymentsApplication>(*args)
}



