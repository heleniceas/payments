package br.com.caju.payments.controller

import br.com.caju.payments.application.TransferApplication
import br.com.caju.payments.controller.request.TransferCreditCardRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

data class ApiResponse(val code: String)

@RestController
class TranferController(
    private val transferApplication: TransferApplication
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("transfer", produces = ["application/json"])
    fun authorize(
        @RequestBody request: TransferCreditCardRequest
    ): ApiResponse {
        transferApplication.withdrawal(request)
        return ApiResponse(code = "00")
    }
}