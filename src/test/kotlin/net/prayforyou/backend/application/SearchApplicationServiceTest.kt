//package net.prayforyou.backend.application
//
//import io.mockk.every
//import io.mockk.impl.annotations.InjectMockKs
//import io.mockk.impl.annotations.MockK
//import io.mockk.junit5.MockKExtension
//import io.mockk.verify
//import net.prayforyou.backend.application.battle.GetBattleStatsService
//import net.prayforyou.backend.application.battle.SearchApplicationService
//import net.prayforyou.backend.domain.battle.BattleGun
//import net.prayforyou.backend.domain.battle.BattleRound
//import net.prayforyou.backend.domain.battle.enums.BattleGunType
//import net.prayforyou.backend.domain.battle.enums.BattleMapType
//import net.prayforyou.backend.domain.user.enums.UserType
//import net.prayforyou.backend.fixture.MakeFixtureUtil
//import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserProvider
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//
//@ExtendWith(MockKExtension::class)
//class SearchApplicationServiceTest {
//
//    @MockK
//    lateinit var userProvider: UserProvider
//
//    @MockK
//    lateinit var getBattleStatsService: GetBattleStatsService
//
//    @InjectMockKs
//    lateinit var searchApplicationService: SearchApplicationService
//
//    @Test
//    fun `유저의 이름을 성공적으로 검색한다`() {
//        every { userProvider.findContainsByNickname(any()) } returns mutableListOf()
//
//        searchApplicationService.searchByNickname("TEST")
//
//        verify(exactly = 1) {
//            userProvider.findContainsByNickname(any())
//        }
//    }
//
//    @Test
//    fun `유저가 사용한 총의 리스트를 사용 순으로 정렬해서 받아온다`() {
//        val user = MakeFixtureUtil.createUser(1, "test", 1, UserType.SUDDEN_BATTLE)
//        val stats = MakeFixtureUtil.createBattleStats(1, BattleMapType.ALL_SUPPLY, user)
//        val gunA = BattleGun(id = 1, battleStats = stats, BattleGunType.ASSIST, useCount = 10)
//        val gunB = BattleGun(id = 2, battleStats = stats, BattleGunType.RIPLE, useCount = 20)
//
//        every { userProvider.findByUserId(any()) } returns user
//        every { getBattleStatsService.getGunByUser(any()) } returns listOf(gunA, gunB)
//
//        val result = searchApplicationService.searchGunByUserId(1)
//
//        verify(exactly = 1) {
//            userProvider.findByUserId(any())
//            getBattleStatsService.getGunByUser(any())
//        }
//
//
//        assertEquals(result[0].type, gunB.type)
//        assertEquals(result[0].useCount, gunB.useCount)
//        assertEquals(result[1].type, gunA.type)
//        assertEquals(result[1].useCount, gunA.useCount)
//    }
//
//    @Test
//    fun `유저의 라운드 별 킬뎃 순으로 정렬해서 받아온다`() {
//        val user = MakeFixtureUtil.createUser(1, "test", 1, UserType.SUDDEN_BATTLE)
//        val stats = MakeFixtureUtil.createBattleStats(1, BattleMapType.ALL_SUPPLY, user)
//        val round1 = BattleRound(id = 1, battleStats = stats, round = 1, kill = 10, death = 0)
//        val round2 = BattleRound(id = 2, battleStats = stats, round = 2, kill = 1, death = 1)
//        val round3 = BattleRound(id = 3, battleStats = stats, round = 3, kill = 1, death = 3)
//
//        every { userProvider.findByUserId(any()) } returns user
//        every { getBattleStatsService.getRoundByUser(any()) } returns listOf(round1, round2, round3)
//
//        val result = searchApplicationService.searchRoundByUserId(1)
//
//        verify(exactly = 1) {
//            userProvider.findByUserId(any())
//            getBattleStatsService.getRoundByUser(any())
//        }
//
//        println(result)
//
//        assertEquals(result[0].kill, 10)
//        assertEquals(result[0].death, 0)
//        assertEquals(result[0].rate, 100.0)
//
//        assertEquals(result[1].kill, 1)
//        assertEquals(result[1].death, 1)
//        assertEquals(result[1].rate, 50.0)
//
//        assertEquals(result[2].kill, 1)
//        assertEquals(result[2].death, 3)
//        assertEquals(result[2].rate, 25.0)
//    }
//
//    @Test
//    fun `유저의 장소 별 킬뎃 순으로 정렬해서 받아온다`() {
//        // TODO 폴리곤 파악후 작성
//    }
//}