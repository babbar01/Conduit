package com.example.api.models.request


import com.example.api.models.entity.AddCommentCreds
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddCommentRequest(
    @Json(name = "comment")
    val comment: AddCommentCreds
)