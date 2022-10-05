package net.prayforyou.backend.domain.event

import com.vladmihalcea.hibernate.type.json.JsonType
import net.prayforyou.backend.domain.event.model.BattleLogJson
import net.prayforyou.backend.domain.event.model.MatchJson
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
@Table(name = "event")
@TypeDef(name = "json", typeClass = JsonType::class)
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Type(type = "json")
    @Column(columnDefinition = "json")
    var matchJson: MatchJson,

    @Type(type = "json")
    @Column(name = "battle_log_json", columnDefinition = "json")
    var battleLogJson: BattleLogJson,

    var matchTime: String,

    var todo: Boolean,

    var createdAt: LocalDateTime,

    @Column(unique = true)
    var matchKey: String


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (id != other.id) return false

        return true
    }
}
