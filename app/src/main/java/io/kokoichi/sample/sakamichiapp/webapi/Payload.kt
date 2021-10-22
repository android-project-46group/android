package io.kokoichi.sample.sakamichiapp.webapi

data class MemberInfos(val members: List<Member>)

data class Member(
    var user_id: Int,
    var user_name: String,
    var birthday: String,
    var height: String,
    var generation: String,
    var blood_type: String,
    var blog_url: String,
    var img_url: String,
)

