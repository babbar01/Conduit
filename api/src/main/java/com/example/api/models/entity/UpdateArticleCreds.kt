package com.example.api.models.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateArticleCreds(
        @Json(name = "title")
        val title: String?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "body")
        val body: String?,
)