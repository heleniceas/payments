package br.com.caju.payments.controller

import br.com.caju.payments.application.TransferApplication
import br.com.caju.payments.controller.request.TransferCreditCardRequest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType

import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TranferControllerTest {

    @MockkBean
    lateinit var transferApplication: TransferApplication

    @Autowired
    lateinit var mockMvc: MockMvc
    @Test
    fun `Should return file url when upload is successful`() {

        val transaction = TransferCreditCardRequest(
            account = "1234567890",
            totalAmount = 1.17,
            mcc = "5832",
            merchant = "TAXI"
        )
        val jsonString = Json.encodeToString(transaction)

        every { transferApplication.withdrawal(any()
        ) }  just  Runs

        mockMvc.post("/localhost:8080/transfer") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonString
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }

        }
    }
}