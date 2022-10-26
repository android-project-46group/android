package jp.mydns.kokoichi0206.sakamichiapp.data.remote

import jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto.*
import retrofit2.mock.BehaviorDelegate

/**
 * Mock interface for accessing the APIs with Retrofit.
 */
class MockSakamichiApi(
    private val delegate: BehaviorDelegate<SakamichiApi>
) : SakamichiApi {

    /**
     * 呼び出し回数を保持するためのカウンター。
     * メンバー名に追加情報として付与する。
     */
    var counter = 0

    override suspend fun getMembers(
        groupName: String,
        apiKey: String
    ): MembersDto {
        val membersDto = MembersDto(
            members = listOf(
                MemberDto(
                    userId = 1,
                    nameName = "秋元 真夏 $counter",
                    birthday = "1993年8月20日",
                    height = "154cm",
                    bloodType = "B型",
                    generation = "1期生",
                    blogUrl = "https://blog.nogizaka46.com/manatsu.akimoto",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/akimotomanatsu.jpeg"
                ),
                MemberDto(
                    userId = 2,
                    nameName = "生田 絵梨花 $counter",
                    birthday = "1997年1月22日",
                    height = "160cm",
                    bloodType = "A型",
                    generation = "1期生",
                    blogUrl = "https://blog.nogizaka46.com/erika.ikuta",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/ikutaerika.jpeg"

                ),
                MemberDto(
                    userId = 3,
                    nameName = "伊藤 理々杏 $counter",
                    birthday = "2002年10月8日",
                    height = "154cm",
                    bloodType = "B型",
                    generation = "3期生",
                    blogUrl = "https://blog.nogizaka46.com/riria.itou",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/itouriria.jpeg"
                ),
                MemberDto(
                    userId = 4,
                    nameName = "岩本 蓮加 $counter",
                    birthday = "2004年2月2日",
                    height = "159cm",
                    bloodType = "B型",
                    generation = "3期生",
                    blogUrl = "https://blog.nogizaka46.com/renka.iwamoto",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/iwamotorenka.jpeg"
                ),
                MemberDto(
                    userId = 5,
                    nameName = "梅澤 美波 $counter",
                    birthday = "1999年1月6日",
                    height = "170cm",
                    bloodType = "A型",
                    generation = "3期生",
                    blogUrl = "https://blog.nogizaka46.com/minami.umezawa",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/umezawaminami.jpeg"
                ),
                MemberDto(
                    userId = 6,
                    nameName = "北野 日奈子 $counter",
                    birthday = "1996年7月17日",
                    height = "158cm",
                    bloodType = "O型",
                    generation = "2期生",
                    blogUrl = "https://blog.nogizaka46.com/hinako.kitano",
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/kitanohinako.jpeg"
                )
            )
        )
        counter++
        return delegate.returningResponse(membersDto).getMembers(groupName)
    }

    override suspend fun getBlogs(groupName: String, apiKey: String): BlogsDto {
        val blogsDto = BlogsDto(
            blogs = listOf(
                BlogDto(
                    name = "秋元 真夏 $counter",
                    blogUrl = "https://blog.nogizaka46.com/manatsu.akimoto",
                    lastBlogImg = "https://kokoichi0206.mydns.jp/imgs/blog/nogi/akimotomanatsu.jpeg",
                    lastUpdatedAt = "2021/08/20 00:00"
                ),
                BlogDto(
                    name = "生田 絵梨花 $counter",
                    blogUrl = "https://blog.nogizaka46.com/erika.ikuta",
                    lastBlogImg = "https://kokoichi0206.mydns.jp/imgs/blog/nogi/ikutaerika.jpeg",
                    lastUpdatedAt = "2021/10/25 19:00"
                ),
                BlogDto(
                    name = "伊藤 理々杏 $counter",
                    blogUrl = "https://blog.nogizaka46.com/riria.itou",
                    lastBlogImg = "https://kokoichi0206.mydns.jp/imgs/blog/nogi/itouriria.jpeg",
                    lastUpdatedAt = "2021/10/08 17:24"
                ),
                BlogDto(
                    name = "岩本 蓮加 $counter",
                    blogUrl = "https://blog.nogizaka46.com/renka.iwamoto",
                    lastBlogImg = "https://kokoichi0206.mydns.jp/imgs/blog/nogi/iwamotorenka.jpeg",
                    lastUpdatedAt = "2021/10/30 22:30"
                ),
                BlogDto(
                    name = "梅澤 美波 $counter",
                    blogUrl = "https://blog.nogizaka46.com/minami.umezawa",
                    lastBlogImg = "https://kokoichi0206.mydns.jp/imgs/blog/nogi/umezawaminami.jpeg",
                    lastUpdatedAt = "2021/10/17 20:30"
                ),
                BlogDto(
                    name = "北野 日奈子 $counter",
                    blogUrl = "https://blog.nogizaka46.com/hinako.kitano",
                    lastBlogImg = "https://kokoichi0206.mydns.jp/imgs/blog/nogi/kitanohinako.jpeg",
                    lastUpdatedAt = "2021/08/16 00:45"
                ),
                BlogDto(
                    name = "久保 史緒里 $counter",
                    blogUrl = "https://blog.nogizaka46.com/shiori.kubo",
                    lastBlogImg = "https://kokoichi0206.mydns.jp/imgs/blog/nogi/kuboshiori.jpeg",
                    lastUpdatedAt = "2021/11/04 20:42"
                )
            )
        )
        counter++
        return delegate.returningResponse(blogsDto).getBlogs(groupName)
    }

    override suspend fun getSongs(groupName: String, apiKey: String): SongsDto {
        TODO("Not yet implemented")
    }

    override suspend fun getPositions(title: String, apiKey: String): PositionsDto {
        TODO("Not yet implemented")
    }

    override suspend fun updateBlog(apiKey: String): UpdateBlogResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun reportIssue(message: String): ReportIssueResponseDto {
        TODO("Not yet implemented")
    }
}
