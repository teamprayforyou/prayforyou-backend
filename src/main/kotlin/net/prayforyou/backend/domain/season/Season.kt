package net.prayforyou.backend.domain.season

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "season")
@Entity
class Season (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    private val seasonNumber: Int,

    private val ranking: Int,

    private val winloosePercent: Double,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)
