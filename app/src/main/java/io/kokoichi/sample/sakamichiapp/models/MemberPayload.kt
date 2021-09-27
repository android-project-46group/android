package io.kokoichi.sample.sakamichiapp.models;

import com.google.firebase.firestore.PropertyName;

import java.util.List;

data class MemberPayload (
    @PropertyName("name_en") val name_en: String? = null,
    @PropertyName("name_ja") val name_ja: String? = null,
    @PropertyName("birthday") val birthday: String? = null,
    @PropertyName("height") val height: String? = null,
    @PropertyName("img_url") val img_url: String? = null,
    @PropertyName("blood_type") val blood_type: String? = null,
    @PropertyName("generation") val generation: String? = null,
)
