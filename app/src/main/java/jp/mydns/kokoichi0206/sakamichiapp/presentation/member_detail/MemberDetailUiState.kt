package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail

/**
 * States for Member Detail UI.
 */
data class MemberDetailUiState(
    var tags: MutableList<String> = mutableListOf(),
    var member: jp.mydns.kokoichi0206.model.Member? = null,
)
