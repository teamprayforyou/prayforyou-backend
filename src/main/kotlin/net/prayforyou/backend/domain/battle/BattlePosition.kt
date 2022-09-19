package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "battle_position")
class BattlePosition(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "polygon", columnDefinition = "polygon")
    var polygon: Polygon,

    @Enumerated(EnumType.STRING)
    @Column(name = "battle_place_type")
    var battlePlaceType: BattlePlaceType
) {
    fun isContainsBattlePosition(x: Double, y: Double): Boolean =
        this.polygon.contains(GeometryFactory().createPoint(Coordinate(x, y)))
}
