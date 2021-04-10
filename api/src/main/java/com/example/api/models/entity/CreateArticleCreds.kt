package com.example.api.models.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateArticleCreds(
    @Json(name = "body")
    val body: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "tagList")
    val tagList: List<String>?,
    @Json(name = "title")
    val title: String
)