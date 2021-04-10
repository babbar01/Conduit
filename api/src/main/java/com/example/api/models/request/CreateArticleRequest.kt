package com.example.api.models.request


import com.example.api.models.entity.CreateArticleCreds
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateArticleRequest(
    @Json(name = "article")
    val article: CreateArticleCreds
)