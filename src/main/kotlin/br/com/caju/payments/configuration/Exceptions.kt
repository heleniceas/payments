package br.com.caju.payments.configuration

class InsufficientBalanceException : RuntimeException("Insufficient balance for the transaction.")
class InsufficientBalanceExceptionForBenefits : RuntimeException("Insufficient balance for the transaction.")
class TransactionFailureException : RuntimeException("Transaction could not be processed.")
