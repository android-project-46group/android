package jp.mydns.kokoichi0206.sakamichiapp.presentation.quiz

import jp.mydns.kokoichi0206.domain.usecase.get_members.GetMembersUseCase
import jp.mydns.kokoichi0206.domain.usecase.quiz_record.RecordUseCases
import jp.mydns.kokoichi0206.quiz.QuizViewModel

/**
 * Mock ViewModel of MemberList Screen
 */
class QuizViewModelWihMock(
    getMembersUseCase: GetMembersUseCase,
    recordUseCase: RecordUseCases,
) : QuizViewModel(
    getMembersUseCase, recordUseCase
) {
    /**
     * Api return example
     *
     * @return MutableList of Member data class
     */
    fun fakeGetMembersApi(): MutableList<jp.mydns.kokoichi0206.model.Member> {
        val fakeRes = mutableListOf<jp.mydns.kokoichi0206.model.Member>()
        val fakeResStr = arrayOf(
            arrayOf("A型", "2期生", "157.5cm", "松田好花", "1999年4月27日"),
            arrayOf("O型", "2期生", "163.5cm", "金村美玖", "2002年9月10日"),
            arrayOf("AB型", "2期生", "157cm", "丹生明里", "2001年2月15日"),
            arrayOf("A型", "2期生", "158.2cm", "渡邉美穂", "2000年2月24日"),
            arrayOf("O型", "1期生", "154.5cm", "影山優佳", "2001年5月8日"),
            arrayOf("B型", "1期生", "162.2cm", "高本彩花", "1998年11月2日"),
            arrayOf("A型", "1期生", "160.5cm", "加藤史帆", "1998年2月2日"),
            arrayOf("O型", "3期生", "151.4cm", "山口陽世", "2004年2月23日"),
            arrayOf("AB型", "3期生", "162.5cm", "上村ひなの", "2004年4月12日"),
            arrayOf("AB型", "3期生", "162.5cm", "上村ひなの", "2004年4月12日"), // Same value
        )
        fakeResStr.forEach {
            fakeRes.add(
                jp.mydns.kokoichi0206.model.Member(
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