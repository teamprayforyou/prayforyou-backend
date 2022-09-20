package net.prayforyou.backend.infrastructure.crawler.webclient

import java.util.Random

object RandomProxy {
    private val proxy = hashMapOf<Int, Proxy>()
    var changeIp = false
    var useProxy = true

    init {
        proxy[1] = Proxy("121.126.26.31",5463)
        proxy[2] = Proxy("124.198.66.222:",294)
        proxy[3] = Proxy("121.126.139.174",5643)
        proxy[4] = Proxy("203.109.9.2",6445)
        proxy[5] = Proxy("49.254.8.2",6994)
        proxy[6] = Proxy("115.144.225.78",5158)
        proxy[7] = Proxy("124.198.89.251",6226)
        proxy[8] = Proxy("115.144.188.157",5198)
        proxy[9] = Proxy("49.254.202.17",6734)
        proxy[10] = Proxy("125.7.175.93",6323)
        proxy[11] = Proxy("125.7.175.95",6325)
        proxy[12] = Proxy("49.254.9.24",7016)
        proxy[13] = Proxy("49.254.224.181",5030)
        proxy[14] = Proxy("124.198.124.229",5078)
        proxy[15] = Proxy("49.254.32.141",6902)
        proxy[16] = Proxy("115.144.43.158",5262)
        proxy[17] = Proxy("203.109.8.235",5427)
        proxy[18] = Proxy("121.126.63.36",5860)
        proxy[19] = Proxy("121.126.64.104",5480)
        proxy[20] = Proxy("124.198.79.49",6176)
        proxy[21] = Proxy("124.198.79.55",6182)
        proxy[22] = Proxy("49.254.151.17",6576)
        proxy[23] = Proxy("115.144.165.9",5130)
        proxy[24] = Proxy("121.126.26.24",5456)
        proxy[25] = Proxy("125.7.143.109",5173)
        proxy[26] = Proxy("115.144.199.42",5234)
        proxy[27] = Proxy("49.254.95.8",7024)
        proxy[28] = Proxy("121.126.93.9",5929)
        proxy[29] = Proxy("203.109.5.109",6433)
        proxy[30] = Proxy("115.144.30.39",5358)
        proxy[31] = Proxy("121.126.224.42",5726)
        proxy[32] = Proxy("121.126.136.105",5622)
        proxy[33] = Proxy("125.7.155.61",6307)
        proxy[34] = Proxy("124.198.43.137",6080)
        proxy[35] = Proxy("125.7.129.243",5539)
        proxy[36] = Proxy("115.144.148.90",5202)
        proxy[37] = Proxy("121.126.95.3",5938)
        proxy[38] = Proxy("125.7.180.254",5213)
        proxy[39] = Proxy("49.254.55.55",6944)
        proxy[40] = Proxy("202.126.114.21",6394)
        proxy[41] = Proxy("125.7.180.251",5210)
        proxy[42] = Proxy("115.144.7.161",5487)
        proxy[43] = Proxy("121.126.17.71",5719)
        proxy[44] = Proxy("115.144.97.251",5092)
        proxy[45] = Proxy("121.126.121.30",5334)
        proxy[46] = Proxy("124.198.36.46",6061)
        proxy[47] = Proxy("121.126.17.65",5713)
        proxy[48] = Proxy("115.144.187.102",5191)
        proxy[49] = Proxy("124.198.46.206",6093)
        proxy[50] = Proxy("121.126.127.166",5595)
        proxy[51] = Proxy("49.254.201.50",6727)
        proxy[52] = Proxy("183.78.129.93",6354)
        proxy[53] = Proxy("124.198.21.54",6021)
        proxy[54] = Proxy("115.144.139.252",5242)
        proxy[55] = Proxy("49.254.193.148",6697)
        proxy[56] = Proxy("121.126.14.34",5647)
        proxy[57] = Proxy("121.126.20.126",5142)
        proxy[58] = Proxy("121.126.133.18",5607)
        proxy[59] = Proxy("121.126.130.26",5599)
        proxy[60] = Proxy("115.144.199.44",5236)
        proxy[61] = Proxy("49.254.98.94",7046)
        proxy[62] = Proxy("115.144.34.89",5360)
        proxy[63] = Proxy("49.254.171.19",6626)
        proxy[64] = Proxy("115.144.193.200",5216)
        proxy[65] = Proxy("121.126.170.69",5681)
        proxy[66] = Proxy("115.144.40.127",5398)
        proxy[67] = Proxy("121.126.65.54",5870)
        proxy[68] = Proxy("124.198.104.127",5974)
        proxy[69] = Proxy("121.126.177.245",5393)
        proxy[70] = Proxy("121.126.190.244",5696)
        proxy[71] = Proxy("121.126.55.202",5043)
        proxy[72] = Proxy("124.198.10.77",5956)
        proxy[73] = Proxy("124.198.94.142",6236)
        proxy[74] = Proxy("202.126.114.17",6390)
        proxy[75] = Proxy("125.7.172.207",5511)
        proxy[76] = Proxy("124.198.114.91",5399)
        proxy[77] = Proxy("121.126.80.180",5900)
        proxy[78] = Proxy("121.126.129.149",5253)
        proxy[79] = Proxy("49.254.226.33",6788)
        proxy[80] = Proxy("124.198.75.39",6174)
        proxy[81] = Proxy("125.7.187.36",6337)
        proxy[82] = Proxy("115.144.54.3",5441)
        proxy[83] = Proxy("49.254.97.48",7032)
        proxy[84] = Proxy("124.198.66.221",5293)
        proxy[85] = Proxy("121.126.25.134",5775)
        proxy[86] = Proxy("121.126.106.170",5466)
        proxy[87] = Proxy("49.254.147.110",5326)
        proxy[88] = Proxy("49.254.152.235",6586)
        proxy[89] = Proxy("49.254.138.189",6548)
        proxy[90] = Proxy("124.198.50.132",6107)
        proxy[91] = Proxy("115.144.184.141",5182)
        proxy[92] = Proxy("49.254.60.42",6955)
    }

    fun getRandomProxy(): Proxy {
        val random = Random()
        val (_, proxy) = proxy.entries.elementAt(random.nextInt(proxy.size))

        return proxy
    }
}

data class Proxy(
    val ip: String,
    val port: Int
)
