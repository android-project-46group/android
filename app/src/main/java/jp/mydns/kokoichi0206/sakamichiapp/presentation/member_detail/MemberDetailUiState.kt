package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Member

/**
 * States for Member Detail UI.
 */
data class MemberDetailUiState(
    var tags: MutableList<String> = mutableListOf(),
    var member: Member? = null,
)
