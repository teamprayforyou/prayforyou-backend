//package net.prayforyou.backend.application
//
//import io.mockk.every
//import io.mockk.impl.annotations.MockK
//import io.mockk.junit5.MockKExtension
//import io.mockk.mockk
//import io.mockk.spyk
//import net.prayforyou.backend.application.battle.SubBattleStatsDto
//import net.prayforyou.backend.domain.battle.*
//import net.prayforyou.backend.domain.battle.enums.BattleGunType
//import net.prayforyou.backend.domain.battle.enums.BattleMapType
//import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
//import net.prayforyou.backend.domain.user.User
//import net.prayforyou.backend.domain.user.enums.UserType
//import net.prayforyou.backend.fixture.MakeFixtureUtil
//import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
//import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattleGunProvider
//import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattlePlaceProvider
//import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattleRoundProvider
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.InjectMocks
//import org.springframework.data.geo.Point
//import org.springframework.data.geo.Polygon
//
//
//@ExtendWith(MockKExtension::class)
//class SaveSubBattleStatsServiceTest {
//
//    @MockK
//    lateinit var battleGunProvider: BattleGunProvider
//
//    @MockK
//    lateinit var battlePlaceProvider: BattlePlaceProvider
//
//    @MockK
//    lateinit var battleRoundProvider: BattleRoundProvider
//
//    @MockK
//    lateinit var getBattlePositionService: GetBattlePositionService
//
//    @MockK
//    lateinit var getBattleStatsService: GetBattleStatsService
//
//    @InjectMocks
//    lateinit var saveSubBattleStatsService: SaveSubBattleStatsService
//
//    @Test
//    fun `kill을 하였을 경우 kill += 1 을 하여 round, place, gun을 업데이트 한다()`() {
//        val user = MakeFixtureUtil.createUser(1, "test", 1, UserType.SUDDEN_BATTLE)
//        val battleStats = MakeFixtureUtil.createBattleStats(1, BattleMapType.ALL_SUPPLY, user)
//        val s = listOf(Point(0.0, 0.0), Point(0.0, 0.0), Point(0.0, 0.0), Point(0.0, 0.0))
//
//
//
//        org.locationtech.jts.geom.Polygon.
//
//        BattlePosition(1, Polygon(s), )
//        MakeFixtureUtil.createAllSubStats()
//
//
//        val stats = spyk<BattleStats>()
//        val position = spyk<BattlePosition>()
//
//        every { getBattleStatsService.getSubStatsByUser(any()) } returns
//                SubBattleStatsDto(
//                    battlePlace = listOf(BattlePlace(1, stats, position, 1, 1)),
//                    battleRound = listOf(BattleRound(1, stats, 1, 1, 1)),
//                    battleGun = listOf(BattleGun(1, stats, BattleGunType.RIPLE, 1))
//                )
//
//        every { getBattlePositionService.getBattlePositionByXandY(any(), any()) } returns BattlePlaceType.SUPPLY01
//
//        saveSubBattleStatsService.saveByKill(
//            BattleLog(
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//            ), stats, user
//        )
//    }
//
//}