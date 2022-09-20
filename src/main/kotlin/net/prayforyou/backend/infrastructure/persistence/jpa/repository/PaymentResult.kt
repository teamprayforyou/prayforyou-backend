package net.prayforyou.backend.infrastructure.persistence.jpa.repository

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

class PaymentResult(
    val paymentId: Long,
    val dummy: PaymentDummy
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class PaymentDummy(
        val channel: String,
        val amount: Int,
        val card: String
    )
}
