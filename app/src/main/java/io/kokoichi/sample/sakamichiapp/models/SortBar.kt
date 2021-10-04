package io.kokoichi.sample.sakamichiapp.models

enum class SortKeyVal(val str: String) {
    SORT_KEY_MESSAGE("並びかえ"),
    SORT_VAL_DEFAULT("選んでください"),
    SORT_VAL_BY_NAME("50音順"),
    SORT_VAL_BY_BIRTHDAY("生年月日"),
    SORT_VAL_BY_BLOOD("血液型"),
}

enum class NarrowKeyVal(val str: String) {
    // 絞り込みを行うための設定
    NARROW_KEY_MESSAGE("絞り込み"),
    NARROW_VAL_NOTHING("なし"),
    NARROW_VAL_FIRST_GEN("1期生"),
    NARROW_VAL_SECOND_GEN("2期生"),
    NARROW_VAL_THIRD_GEN("3期生"),
    NARROW_VAL_FOURTH_GEN("4期生"),
}


val NOGI_NARROW_VALS = listOf(
    NarrowKeyVal.NARROW_VAL_FIRST_GEN,
    NarrowKeyVal.NARROW_VAL_SECOND_GEN,
    NarrowKeyVal.NARROW_VAL_THIRD_GEN,
    NarrowKeyVal.NARROW_VAL_FOURTH_GEN
)
val SAKURA_NARROW_VALS = listOf(
    NarrowKeyVal.NARROW_VAL_FIRST_GEN,
    NarrowKeyVal.NARROW_VAL_SECOND_GEN
)
val HINATA_NARROW_VALS =
    listOf(
        NarrowKeyVal.NARROW_VAL_FIRST_GEN,
        NarrowKeyVal.NARROW_VAL_SECOND_GEN,
        NarrowKeyVal.NARROW_VAL_THIRD_GEN
    )