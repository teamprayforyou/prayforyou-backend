package net.prayforyou.backend.infrastructure.persistence.jpa.provider.banner

import net.prayforyou.backend.domain.banner.Banner
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.banner.BannerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class BannerProvider(
    private val bannerRepository: BannerRepository
) {

    fun findBanner(): List<Banner> =
        bannerRepository.findAllByIsDeletedFalse()
}