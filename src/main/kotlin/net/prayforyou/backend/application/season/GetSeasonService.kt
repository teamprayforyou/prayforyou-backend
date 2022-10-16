package net.prayforyou.backend.application.season

import net.prayforyou.backend.global.util.DateUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate


@Service
@Transactional
class GetSeasonService {

    // TODO 데이터 넣어서 하기
    fun getRemainSeason(): Long {
        val endSeason = LocalDate.of(2023, 2, 28)
        return DateUtil.getRemainDay(endSeason)
    }
}