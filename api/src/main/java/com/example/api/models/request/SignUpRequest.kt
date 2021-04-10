package com.example.api.models.request

import com.example.api.models.entity.SignUpCreds
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    @Json(name = "user")
    val user : SignUpCreds
)
