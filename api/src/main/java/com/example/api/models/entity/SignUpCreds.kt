package com.example.api.models.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.print.attribute.standard.JobOriginatingUserName

@JsonClass(generateAdapter = true)
data class SignUpCreds(
    @Json(name = "username")
    val username: String,
    @Json(name = "email")
    val email:String,
    @Json(name = "password")
    val password:String
)
