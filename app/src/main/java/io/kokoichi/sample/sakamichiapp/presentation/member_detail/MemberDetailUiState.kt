package io.kokoichi.sample.sakamichiapp.presentation.member_detail

import io.kokoichi.sample.sakamichiapp.domain.model.Member

/**
 * States for Member Detail UI.
 */
data class MemberDetailUiState(
    var tags: MutableList<String> = mutableListOf(),
    var member: Member? = null,
)
