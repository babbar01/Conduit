package com.example.api.models.request


import com.example.api.models.entity.UpdateUserCreds
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserUpdateRequest(
    @Json(name = "user")
    val user: UpdateUserCreds
)