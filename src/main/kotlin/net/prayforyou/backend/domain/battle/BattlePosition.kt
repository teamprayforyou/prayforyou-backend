package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "battle_position")
class BattlePosition (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // column datatype polygon 으로 변경할것
    var polygon: Polygon,

    @Enumerated(EnumType.STRING)
    var battlePlaceType: BattlePlaceType,
) {
    fun isContainsBattlePosition(x: Double, y: Double): Boolean {
        return this.polygon.contains(GeometryFactory().createPoint(Coordinate(x, y)))
    }
}
