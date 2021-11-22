package io.kokoichi.sample.sakamichiapp.presentation.member_list

import io.kokoichi.sample.sakamichiapp.domain.model.Member

/**
 * States for the web API return value.
 */
data class MemberListApiState(
    var isLoading: Boolean = false,
    var members: MutableList<Member> = mutableListOf(),
    var error: String = ""
)
