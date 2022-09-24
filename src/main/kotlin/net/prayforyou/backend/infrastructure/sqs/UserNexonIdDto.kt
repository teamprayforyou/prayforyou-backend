package net.prayforyou.backend.infrastructure.sqs

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class UserNexonIdDto {
    val userNexonId: String? = null
}
