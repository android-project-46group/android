package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Member

/**
 * States for the web API return value.
 */
data class MemberListApiState(
    var isLoading: Boolean = false,
    var members: MutableList<Member> = mutableListOf(),
    var error: String = ""
)
