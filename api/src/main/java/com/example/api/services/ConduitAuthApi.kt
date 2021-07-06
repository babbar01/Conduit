package com.example.api.services

import com.example.api.models.request.AddCommentRequest
import com.example.api.models.request.CreateArticleRequest
import com.example.api.models.request.UpdateArticleRequest
import com.example.api.models.request.UserUpdateRequest
import com.example.api.models.response.*
import retrofit2.Response
import retrofit2.http.*


interface ConduitAuthApi {

    @GET("articles")
    suspend fun getArticles(
        @Query("tag") tag: String? = null,
        @Query("favorited") favourited: String? = null,
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

    @GET("user")
    suspend fun getCurrentUser(): Response<UserResponse>

    @PUT("user")
    suspend fun updateUser(
            @Body updateRequest: UserUpdateRequest
    ): Response<UserResponse>

    @GET("profiles/{username}")
    suspend fun getProfile(
            @Path("username") username: String
    ): Response<ProfileResponse>

    @POST("profiles/{username}/follow")
    suspend fun followUser(
        @Path("username") username: String
    ) : Response<ProfileResponse>

    @DELETE("profiles/{username}/follow")
    suspend fun unfollowUser(
            @Path("username") username: String
    ): Response<ProfileResponse>

    // article

    @GET("articles/feed")
    suspend fun getFeedArticles(): Response<ArticlesResponse>

    @POST("articles")
    suspend fun createArticle(
            @Body createArticleRequest: CreateArticleRequest
    ): Response<ArticleResponse>

    @PUT("articles/{slug}")   // will update the slug also
    suspend fun updateArticle(
            @Path("slug") slug: String,
            @Body updateArticleRequest: UpdateArticleRequest
    ): Response<ArticleResponse>

    @DELETE("/articles/{slug}")
    suspend fun deleteArticle(@Path("slug") slug: String)

    // comment

    @POST("api/articles/{slug}/comments")
    suspend fun addComment(
            @Path("slug") slug: String,
            @Body addCommentRequest: AddCommentRequest
    ): Response<CommentResponse>

    @DELETE("articles/{slug}/comments/{id}")
    suspend fun deleteComment(
            @Path("slug") slug: String,
            @Path("id") commentId: String
    )

    //favourite

    @POST("articles/{slug}/favorite")
    suspend fun favouriteArticle(
            @Path("slug") slug: String
    ): Response<ArticleResponse>

    @DELETE("articles/{slug}/favorite")
    suspend fun unfavouriteArticle(
            @Path("slug") slug: String
    ): Response<ArticleResponse>


}