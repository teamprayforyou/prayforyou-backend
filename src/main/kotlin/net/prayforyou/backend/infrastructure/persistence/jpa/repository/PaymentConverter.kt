package net.prayforyou.backend.infrastructure.persistence.jpa.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class PaymentConverter : AttributeConverter<PaymentResult, String> {

    private companion object {
        val objectMapper = ObjectMapper().registerKotlinModule()
    }

    override fun convertToDatabaseColumn(attribute: PaymentResult?): String? =
        try {
            objectMapper.writeValueAsString(attribute)
        } catch (e: Exception) {
            null
        }

    override fun convertToEntityAttribute(dbData: String?): PaymentResult? =
        try {
            dbData?.let {
                objectMapper.readValue<PaymentResult>(dbData)
            }
        } catch (e: Exception) {
            null
        }
}