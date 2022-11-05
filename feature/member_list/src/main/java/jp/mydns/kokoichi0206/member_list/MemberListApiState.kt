package jp.mydns.kokoichi0206.member_list

/**
 * States for the web API return value.
 */
data class MemberListApiState(
    var isLoading: Boolean = false,
    var members: MutableList<jp.mydns.kokoichi0206.model.Member> = mutableListOf(),
    var error: String = ""
)
