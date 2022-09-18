package net.prayforyou.backend.fixture

import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType

class MakeFixtureUtil {
    companion object {
        fun createUser(id: Long, nickname: String, nexonId: Int, type: UserType): User =
            User(id, nickname, nexonId, type)

        fun createBattleStats(id: Long, type: BattleMapType, user: User): BattleStats =
            BattleStats(id = id, user = user, mapType = type)
    }
}