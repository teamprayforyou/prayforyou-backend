package net.prayforyou.backend.presenter.response

import net.prayforyou.backend.application.banner.dto.GetBannerDto

data class BannerResponse(
    val typeA: BannerDetailResponse?,
    val typeB: BannerDetailResponse?,
    val typeC: BannerDetailResponse?
) {
    data class BannerDetailResponse(
        val imgUrl: String?,
        val siteUrl: String?,
    )

    companion object {
        fun convert(bannerDto: GetBannerDto): BannerResponse =
            BannerResponse(
                typeA = BannerDetailResponse(
                    imgUrl = bannerDto.typeA?.imageUrl,
                    siteUrl = bannerDto.typeA?.siteUrl
                ),
                typeB = BannerDetailResponse(
                    imgUrl = bannerDto.typeB?.imageUrl,
                    siteUrl = bannerDto.typeB?.siteUrl
                ),
                typeC = BannerDetailResponse(
                    imgUrl = bannerDto.typeC?.imageUrl,
                    siteUrl = bannerDto.typeC?.imageUrl
                )
            )
    }
}