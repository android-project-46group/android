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

data class Positions(val positions: List<PositionProp>)

data class PositionProp(
    var member_name: String,
    var img_url: String,
    var position: String,
    var is_center: Boolean,
)

data class Songs(var songs: List<SongProp>)

data class SongProp(
    var single: String,
    var title: String,
    var center: String,
)
