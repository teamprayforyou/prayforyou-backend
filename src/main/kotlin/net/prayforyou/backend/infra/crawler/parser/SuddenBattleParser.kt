package net.prayforyou.backend.infra.crawler.parser

import net.prayforyou.backend.infra.crawler.webclient.client.SuddenBattleClient
import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class SuddenBattleParser(
    private val suddenBattleClient: SuddenBattleClient
) {

    fun parseClanLink(): MutableList<String>? {
        val suddenBattleHtml = suddenBattleClient.fetchClan()
        val jsoup = Jsoup.parse(suddenBattleHtml!!)
        val eachAttr = jsoup.select("td.viewcname > a").eachAttr("href")
        val regex = "\\((.*?)\\)".toRegex()
        return eachAttr.stream().map {
            regex.find(it)!!.value
            .replace(",", "")
            .replace("(", "")
            .replace(")", "")
            .replace("'", "")
            .replaceFirst(".$".toRegex(), "")
        }.toList()
    }

    fun parseClanId(): MutableList<String> {
        val parseClan = parseClanLink()
        val clanIdList = mutableListOf<String>()
        for (clanId in parseClan!!) {
            val fetchUser = suddenBattleClient.fetchUser(clanId)
            val regex = ".*javascript:clanhome\\('(.*)'\\).*".toRegex()
            val target = Jsoup.parse(fetchUser!!).select("td").eachAttr("onclick")[0]
            val find = regex.find(target)
            val (clanId) = find!!.destructured
            clanIdList.add(clanId)
        }
        return clanIdList
    }
}
