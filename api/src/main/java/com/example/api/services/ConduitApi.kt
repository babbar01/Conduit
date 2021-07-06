package com.example.api.services

import com.example.api.models.request.LoginRequest
import com.example.api.models.request.SignUpRequest
import com.example.api.models.response.*
import retrofit2.Response
import retrofit2.http.*

interface ConduitApi {

    @GET("tags")
    suspend fun getTags(): Response<TagsResponse>

    @POST("users/login")
    suspend fun loginUser(
            @Body loginRequest: LoginRequest
    ): Response<UserResponse>

    @POST("users")
    suspend fun signupUser(
            @Body signUpRequest: SignUpRequest
    ): Response<UserResponse>

}