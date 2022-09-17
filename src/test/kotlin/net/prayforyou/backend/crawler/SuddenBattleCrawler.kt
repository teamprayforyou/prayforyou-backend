package net.prayforyou.backend.crawler

import net.prayforyou.backend.domain.battlelog.service.SaveBattleLogService
import net.prayforyou.backend.domain.model.UserType
import net.prayforyou.backend.infra.crawler.parser.SuddenBattleParser
import net.prayforyou.backend.infra.crawler.webclient.client.ClanUserClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SuddenBattleCrawler {

    @Autowired
    lateinit var suddenBattleParser: SuddenBattleParser

    @Autowired
    lateinit var clanUserClient: ClanUserClient

    @Autowired
    lateinit var saveBattleLogService: SaveBattleLogService

    @Test
    fun `서든 배틀 유저의 모든 배틀로그 저장`() {
//        val userInfoIdList = listOf(688126756, 2130928828, 839766504, 822973585, 268998766, 2131217705, 487058313, 1292020621, 520554665, 738827387, 1258577867, 1929802232, 1040536479, 1963205187, 1074214292, 17371036, 1862789456, 1258485150, 118142922, 1510459135, 1627694823, 856034323, 1376415970, 100984860, 1745282132, 1443671525, 470143761, 638347851, 1342300026, 185372241, 2063835418, 1141272648, 1175230811, 1745315743, 1560662599, 1057583031, 2064194993, 1812621315, 587916038, 738635904, 1342518816, 1929453627, 2114531626, 453260964, 1644472872, 704925029, 168101067, 671230971, 571051416, 1191449839, 839382853, 235226622, 436861146, 1007129213, 671951064, 922824933, 1175295247, 1476877094, 1090800164, 184707081, 319390897, 84187159, 1426368627, 1845899119, 772169623, 353159103, 1074208042, 654666888)

        val parseClanId = suddenBattleParser.parseClanId()
        val userInfoIdList = clanUserClient.fetchUserInfoIdListByClanId(parseClanId)
        for (userInfoId in userInfoIdList) {
            println("유저 정보 id 값 $userInfoId")
            saveBattleLogService.saveBattleLogByUserId(userInfoId!!, UserType.SUDDEN_BATTLE)
        }
    }
}
