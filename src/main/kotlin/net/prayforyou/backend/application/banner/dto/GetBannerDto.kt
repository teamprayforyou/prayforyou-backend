package net.prayforyou.backend.application.banner.dto

import net.prayforyou.backend.domain.banner.Banner

data class GetBannerDto(
    val typeA: Banner?,
    val typeB: Banner?,
    val typeC: Banner?
)