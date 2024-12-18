package net.prayforyou.backend.domain.user

import com.vladmihalcea.hibernate.type.json.JsonType
import net.prayforyou.backend.domain.user.model.UserJsonModel
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_json")
@TypeDef(name = "json", typeClass = JsonType::class)
class UserJson (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Type(type = "json")
    @Column(columnDefinition = "json")
    val userJson: UserJsonModel,

    val createdAt: LocalDateTime,

    val clanId: String,

    var todo: Boolean
)
