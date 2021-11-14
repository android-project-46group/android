package io.kokoichi.sample.sakamichiapp.data.remote

import io.kokoichi.sample.sakamichiapp.BuildConfig
import io.kokoichi.sample.sakamichiapp.data.remote.dto.MemberDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.MembersDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.PositionsDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.SongsDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.mock.BehaviorDelegate

/**
 * Mock interface for accessing the APIs with Retrofit.
 */
class MockSakamichiApi(
    private val delegate: BehaviorDelegate<SakamichiApi>
) : SakamichiApi {

    override suspend fun getMembers(
        groupName: String,
        apiKey: String
    ): MembersDto {
        val membersDto = MembersDto(
            members = listOf(
                MemberDto(
                    userId = 1,
                    nameName = "秋元 真夏",
                    birthday = "1993年8月20日",
                    height = "154cm",
                    bloodType = "B型",
                    generation = "1期生",
                    blogUrl = "https://blog.nogizaka46.com/manatsu.akimoto",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/akimotomanatsu.jpeg"
                ),
                MemberDto(
                    userId = 2,
                    nameName = "生田 絵梨花",
                    birthday = "1997年1月22日",
                    height = "160cm",
                    bloodType = "A型",
                    generation = "1期生",
                    blogUrl = "https://blog.nogizaka46.com/erika.ikuta",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/ikutaerika.jpeg"

                ),
                MemberDto(
                    userId = 3,
                    nameName = "伊藤 理々杏",
                    birthday = "2002年10月8日",
                    height = "154cm",
                    bloodType = "B型",
                    generation = "3期生",
                    blogUrl = "https://blog.nogizaka46.com/riria.itou",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/itouriria.jpeg"
                ),
                MemberDto(
                    userId = 4,
                    nameName = "岩本 蓮加",
                    birthday = "2004年2月2日",
                    height = "159cm",
                    bloodType = "B型",
                    generation = "3期生",
                    blogUrl = "https://blog.nogizaka46.com/renka.iwamoto",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/iwamotorenka.jpeg"
                ),
                MemberDto(
                    userId = 5,
                    nameName = "梅澤 美波",
                    birthday = "1999年1月6日",
                    height = "170cm",
                    bloodType = "A型",
                    generation = "3期生",
                    blogUrl = "https://blog.nogizaka46.com/minami.umezawa",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/umezawaminami.jpeg"
                ),
                MemberDto(
                    userId = 6,
                    nameName = "北野 日奈子",
                    birthday = "1996年7月17日",
                    height = "158cm",
                    bloodType = "O型",
                    generation = "2期生",
                    blogUrl = "https://blog.nogizaka46.com/hinako.kitano",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/kitanohinako.jpeg"
                )
            )
        )
        return delegate.returningResponse(membersDto).getMembers(groupName)
    }

    override suspend fun getSongs(groupName: String, apiKey: String): SongsDto {
        TODO("Not yet implemented")
    }

    override suspend fun getPositions(title: String, apiKey: String): PositionsDto {
        TODO("Not yet implemented")
    }
}
