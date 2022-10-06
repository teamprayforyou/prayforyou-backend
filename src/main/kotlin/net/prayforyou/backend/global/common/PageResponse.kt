package net.prayforyou.backend.global.common

class PageResponse<T>(
    val data: T?,
    val message: String?,
    val isLast: Boolean? = false,
    val totalPages: Int? = null
) {
    companion object {
        fun <T> convert(data: T, isLast: Boolean?, totalPages: Int?): PageResponse<T> =
            PageResponse(
                data = data,
                message = null,
                isLast = isLast,
                totalPages = totalPages
            )

        fun error(message: String): PageResponse<Nothing> =
            PageResponse(
                data = null,
                message = message
            )
    }
}
