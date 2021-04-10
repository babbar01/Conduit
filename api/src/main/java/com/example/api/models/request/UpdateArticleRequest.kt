package com.example.api.models.request


import com.example.api.models.entity.UpdateArticleCreds
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateArticleRequest(
    @Json(name = "article")
    val article: UpdateArticleCreds
)