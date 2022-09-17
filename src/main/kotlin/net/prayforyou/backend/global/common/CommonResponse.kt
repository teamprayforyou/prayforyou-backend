package net.prayforyou.backend.global.common

class CommonResponse<T>(
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> convert(data: T): CommonResponse<T> =
            CommonResponse(
                data = data,
                message = null
            )

        fun error(message: String): CommonResponse<Nothing> =
            CommonResponse(
                data = null,
                message = message
            )
    }
}