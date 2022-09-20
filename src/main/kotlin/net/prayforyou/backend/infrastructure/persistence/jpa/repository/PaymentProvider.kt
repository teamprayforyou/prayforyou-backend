package net.prayforyou.backend.infrastructure.persistence.jpa.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentProvider : JpaRepository<Payment, Long> {
}