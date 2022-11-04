package jp.mydns.kokoichi0206.sakamichiapp.presentation.positions

import jp.mydns.kokoichi0206.positions.PositionsViewModel
import usecase.get_positions.GetPositionsUseCase
import usecase.get_songs.GetSongsUseCase

class PositionsViewModelWithMock(
    getPositionsUseCase: usecase.get_positions.GetPositionsUseCase,
    getSongsUseCase: usecase.get_songs.GetSongsUseCase,
) : PositionsViewModel(
    getPositionsUseCase = getPositionsUseCase,
    getSongsUseCase = getSongsUseCase,
){

    private val sampleData = listOf<jp.mydns.kokoichi0206.model.Position>(
        jp.mydns.kokoichi0206.model.Position(
            memberName = "潮 紗理菜",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/ushiosarina.jpeg",
            position = "070",
            isCenter = false
        ),
        jp.mydns.kokoichi0206.model.Position(
            memberName = "影山 優佳",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/kageyamayuuka.jpeg",
            position = "020",
            isCenter = false
        ),
        jp.mydns.kokoichi0206.model.Position(
            memberName = "加藤 史帆",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/katoushiho.jpeg",
            position = "004",
            isCenter = false
        ),
        jp.mydns.kokoichi0206.model.Position(
            memberName = "齊藤 京子",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/saitoukyouko.jpeg",
            position = "002",
            isCenter = false
        ),
        jp.mydns.kokoichi0206.model.Position(
            memberName = "佐々木 久美",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakikumi.jpeg",
            position = "010",
            isCenter = false
        ),
        jp.mydns.kokoichi0206.model.Position(
            memberName = "佐々木 美玲",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakimirei.jpeg",
            position = "040",
            isCenter = false
        ),
        jp.mydns.kokoichi0206.model.Position(
            memberName = "高瀬 愛奈",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takasemana.jpeg",
            position = "200",
            isCenter = false
        ),
        jp.mydns.kokoichi0206.model.Position(
            memberName = "高本 彩花",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takamotoayaka.jpeg",
            position = "400",
            isCenter = false
        ),
        jp.mydns.kokoichi0206.model.Position(
            memberName = "東村 芽依",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/higashimuramei.jpeg",
            position = "001",
            isCenter = false
        ),
        jp.mydns.kokoichi0206.model.Position(
            memberName = "金村 美玖",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/kanemuramiku.jpeg",
            position = "003",
            isCenter = true
        ),
    )

    fun setSampleSongs() {
        positionApiState.value.positions = sampleData
    }
}