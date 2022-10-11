package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Member
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_members.GetMembersUseCase

/**
 * Mock ViewModel of MemberList Screen
 */
class MemberListViewModelWihMock(
    getMembersUseCase: GetMembersUseCase
) : MemberListViewModel(
    getMembersUseCase
) {
    /**
     * Api return example
     *
     * @return MutableList of Member data class
     */
    fun fakeGetMembersApi(): MutableList<Member> {
        val fakeRes = mutableListOf<Member>()
        val fakeResStr = arrayOf(
            arrayOf("A型", "2期生", "157.5cm", "松田好花", "1999年4月27日"),
            arrayOf("O型", "2期生", "163.5cm", "金村美玖", "2002年9月10日"),
            arrayOf("AB型", "2期生", "157cm", "丹生明里", "2001年2月15日"),
            arrayOf("A型", "2期生", "158.2cm", "渡邉美穂", "2000年2月24日"),
            arrayOf("O型", "1期生", "154.5cm", "影山優佳", "2001年5月8日"),
            arrayOf("B型", "1期生", "162.2cm", "高本彩花", "1998年11月2日"),
            arrayOf("A型", "1期生", "160.5cm", "加藤史帆", "1998年2月2日"),
            arrayOf("O型", "3期生", "151.4ccm", "山口陽世", "2004年2月23日"),
            arrayOf("AB型", "3期生", "162.5cm", "上村ひなの", "2004年4月12日"),
            arrayOf("AB型", "3期生", "162.5cm", "上村ひなの", "2004年4月12日"), // Same value
        )
        fakeResStr.forEach {
            fakeRes.add(
                Member(
                    blogUrl = "https://example.com",
                    bloodType = it[0],
                    generation = it[1],
                    height = it[2],
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/matsudakonoka.jpeg",
                    name = it[3],
                    birthday = it[4],
                )
            )
        }
        return fakeRes
    }

    /**
     * Custom members using 2-dim array.
     * Use this when you need data other than fakeGetMembersApi().
     *
     * @return MutableList of Member data class
     */
    fun getCustomMembers(membersInfo: Array<Array<String>>): MutableList<Member> {
        val fakeRes = mutableListOf<Member>()
        membersInfo.forEach {
            fakeRes.add(
                Member(
                    blogUrl = "https://example.com",
                    bloodType = it[0],
                    generation = it[1],
                    height = it[2],
                    imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/matsudakonoka.jpeg",
                    name = it[3],
                    birthday = it[4],
                )
            )
        }
        return fakeRes
    }
}