package com.example.api.services

import com.example.api.models.request.LoginRequest
import com.example.api.models.request.SignUpRequest
import com.example.api.models.response.*
import retrofit2.Response
import retrofit2.http.*

interface ConduitApi {

    @GET("articles")
    suspend fun getArticles(
            @Query("tag") tag: String? = null,
            @Query("favourited") favourited: String? = null,
            @Query("author") author: String? = null
    ): Response<ArticlesResponse>

    @GET("articles/{slug}")
    suspend fun getArticlesBySlug(
            @Path("slug") slug: String
    ): Response<ArticleResponse>

    @GET("articles/{slug}/comments")
    suspend fun getCommentsOnArticle(
            @Path("slug") slug: String
    ): Response<CommentsResponse>

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