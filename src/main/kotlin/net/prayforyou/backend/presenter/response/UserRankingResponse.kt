package net.prayforyou.backend.presenter.response

import net.prayforyou.backend.domain.user.User

data class UserRankingResponse(
    val userNexonId: Int,
    val name: String,
    val clanMarkUrl: String? = null,
    val winCount: Int,
    val loseCount: Int,
    val winLosePercent: Double,
    val killDeath: Double,
    val killPerGame: Double,
    val score: Long
) {
    companion object {
        fun from(user: User): UserRankingResponse {
            return UserRankingResponse(
                userNexonId = user.userNexonId,
                name = user.nickname!!,
                clanMarkUrl = user.clanId?.clanMarkUrl
                    ?: "https://prayforyou.s3.ap-northeast-2.amazonaws.com/x%20%281%29.svg?response-content-disposition=inline&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEEUaDmFwLW5vcnRoZWFzdC0yIkgwRgIhAI17Lw8rjzQ7Kqqx6Bmegs0fxyh4blf%2BdkLQT0eaguK7AiEAr7J7r6dHi3J9Erj%2BSnIBVdCxT7PdG1sF%2F7ntnmph3cEq7QII%2Fv%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARADGgwyNDkzNDgwMjAzMTAiDKUhdUevAEM69RU2eirBAvZ0IsIoW7m0qMRzHgYpYd4sL6ANkxqHlCKUoAho1PeRlbidFfTRVgNyn%2B%2B%2F%2FHK%2F3flYbwdn4VTnHFpQC%2FFHulRSIzWHFeVacv%2FMK8FGjvWPcCViQ1TAOUMZZPI2bDmpvK9Dev6gz443Z4JgtN%2B%2FI%2B4dgQWwWn6BJw4P8XmC7nWTKYWXmvkAtJZZfPfryBe4B7kFFaSjHYQj9m9ycCkLSdK9tQK81pTZHDEfHiS6DvjGzSgY7WL%2BLtVJNZSurLFo9vxfyWeDxnD8kymZbaH23Hmh35oGt06i%2BmFZOnFzHfFOUNMEUwvqi%2BYDfqA9TNtXCyrhB%2B1E4gXH%2FL%2FQVd4K1ry12GGE5F2VuNw5nW77QwtxuCW64nj5Yuq9w39FQovKDeTTkW5y2EU5gtBY9T7M6bW4CuDRpsXiyuxXDI7QJb3t%2FDDg5%2F6ZBjqyAs7a9kHIBeYUWEBivjil3S%2Bw4Pqk23kYXCPQrPYtcRX%2FyU3s%2BmdK2j4%2FjSXRoMHxm2K2tntenF34ozTxqmpPgzjC3nPPiCeDwrfeap0gUE5jto%2FHJbwGXp1n%2FU61UJbs%2Bg8PnVmJ%2B%2FZD%2FD%2BeJAOGqK9zurXBW28mixcn6BOA%2FSvXdO3uE88r6GnvurEQ53RqcY0qNuu%2BzT4h4LucJ2569Rhq92IsI%2B36DyVmtICtpVVXcZrPRdOsiInEhsBL%2BLv%2F5hiQjWUoq1uabZ86Ra9wW5GcblAgMeKsmcb2gfUIZ4HFhkDmGzhyhE%2BCu9XRK5zWXBP3D2Mo07lK3MNAuAG9E12dJFPK3UUsJ2NSrSs7yNs3mNWMtHwAoMiTWzvNpXYF%2F9l5Q%2FTWT3wxqa6Q%2FQ5uXtY%2BGQ%3D%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221007T052008Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=ASIATUDSM3BLCMXD4RAV%2F20221007%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=402d5223ebc9a1fd06e140a2917e1b9932fa667b40bc6f31bd6672e6bd8eb7eb",
                winCount = user.winCount!!,
                loseCount = user.gameCount!! - user.winCount!!,
                winLosePercent = user.winLoosePercent!!,
                killDeath = user.killDeath!!,
                killPerGame = user.killPerGame!!,
                score = user.score!!
            )
        }
    }
}
