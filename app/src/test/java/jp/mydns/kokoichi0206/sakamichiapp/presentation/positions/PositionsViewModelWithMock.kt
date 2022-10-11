package jp.mydns.kokoichi0206.sakamichiapp.presentation.positions

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Position
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_positions.GetPositionsUseCase
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_songs.GetSongsUseCase

class PositionsViewModelWithMock(
    getPositionsUseCase: GetPositionsUseCase,
    getSongsUseCase: GetSongsUseCase,
) : PositionsViewModel(
    getPositionsUseCase = getPositionsUseCase,
    getSongsUseCase = getSongsUseCase,
){

    private val sampleData = listOf<Position>(
        Position(
            memberName = "潮 紗理菜",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/ushiosarina.jpeg",
            position = "070",
            isCenter = false
        ),
        Position(
            memberName = "影山 優佳",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/kageyamayuuka.jpeg",
            position = "020",
            isCenter = false
        ),
        Position(
            memberName = "加藤 史帆",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/katoushiho.jpeg",
            position = "004",
            isCenter = false
        ),
        Position(
            memberName = "齊藤 京子",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/saitoukyouko.jpeg",
            position = "002",
            isCenter = false
        ),
        Position(
            memberName = "佐々木 久美",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakikumi.jpeg",
            position = "010",
            isCenter = false
        ),
        Position(
            memberName = "佐々木 美玲",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakimirei.jpeg",
            position = "040",
            isCenter = false
        ),
        Position(
            memberName = "高瀬 愛奈",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takasemana.jpeg",
            position = "200",
            isCenter = false
        ),
        Position(
            memberName = "高本 彩花",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takamotoayaka.jpeg",
            position = "400",
            isCenter = false
        ),
        Position(
            memberName = "東村 芽依",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/higashimuramei.jpeg",
            position = "001",
            isCenter = false
        ),
        Position(
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