package net.prayforyou.backend.application

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class GetBattlePositionServiceTest {

    @Autowired
    lateinit var getBattlePositionService: GetBattlePositionService

    @Test
    fun `사용자 x, y 좌표를 통해 포지션을 가져오는지`() {
        val battlePositionByXandY = getBattlePositionService.getBattlePositionByXandY(166, 191)
        println(battlePositionByXandY!!.battlePlaceType!!.name)
    }
}
