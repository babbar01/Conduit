package com.example.api.models.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginCreds(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)