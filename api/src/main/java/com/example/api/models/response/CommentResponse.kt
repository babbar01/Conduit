package com.example.api.models.response


import com.example.api.models.entity.Comment
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentResponse(
        @Json(name = "comment")
        val comment: Comment
)