package net.prayforyou.backend.infrastructure.persistence.jpa.repository

import javax.persistence.*

@Entity
@Table(name = "payment")
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Convert(converter = PaymentConverter::class)
    var payment: PaymentResult
)