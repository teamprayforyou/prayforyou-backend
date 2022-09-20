package net.prayforyou.backend.infrastructure.persistence.jpa.repository.banner

import net.prayforyou.backend.domain.banner.Banner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BannerRepository : JpaRepository<Banner, Long> {
    fun findAllByIsDeletedFalse(): List<Banner>
}