package net.prayforyou.backend.application

import net.prayforyou.backend.infrastructure.persistence.jpa.repository.Payment
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.PaymentProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.PaymentResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull


@SpringBootTest
class Test {

    @Autowired
    lateinit var paymentProvider: PaymentProvider


    @org.junit.jupiter.api.Test
    fun test() {

        val payment = Payment(
            id = 1L,
            payment = PaymentResult(
                paymentId = 1L,
                PaymentResult.PaymentDummy(
                    channel = "test",
                    amount = 100,
                    card = "test"
                )
            )
        )


        paymentProvider.save(payment)

        val result = paymentProvider.findByIdOrNull(1L)



    }
}