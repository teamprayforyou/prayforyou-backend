package net.prayforyou.backend.global.common

import net.bytebuddy.TypeCache.Sort

data class PageableResponse(
    val totalPage: Long,
    val sort: Sort,
    val size: Int
)