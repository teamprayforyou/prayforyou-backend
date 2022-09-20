package net.prayforyou.backend.application.banner

import net.prayforyou.backend.application.banner.dto.GetBannerDto
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.banner.BannerProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class GetBannerService(
    private val bannerProvider: BannerProvider
) {

    fun getBannerByDateTIme(now: LocalDateTime = LocalDateTime.now()): GetBannerDto {
        val allBanner = bannerProvider.findBanner()
            .filter { it.isAvailable(now) }

        return GetBannerDto(
            typeA = allBanner.firstOrNull { it.isTypeA() },
            typeB = allBanner.firstOrNull { it.isTypeB() },
            typeC = allBanner.firstOrNull { it.isTypeC() }
        )
    }
}