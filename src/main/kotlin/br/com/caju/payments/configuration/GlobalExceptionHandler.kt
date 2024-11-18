package br.com.caju.payments.configuration

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleInsufficientBalance(exception: InsufficientBalanceException): ResponseEntity<Map<String, String>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mapOf("code" to "51"))
    }

    @ExceptionHandler(TransactionFailureException::class)
    fun handleTransactionFailure(exception: TransactionFailureException): ResponseEntity<Map<String, String>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mapOf("code" to "07"))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(exception: Exception): ResponseEntity<Map<String, String>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mapOf("code" to "07"))
    }
}