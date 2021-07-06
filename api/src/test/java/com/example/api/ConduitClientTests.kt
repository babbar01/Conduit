package com.example.api

import com.example.api.models.entity.AddCommentCreds
import com.example.api.models.entity.LoginCreds
import com.example.api.models.entity.SignUpCreds
import com.example.api.models.request.AddCommentRequest
import com.example.api.models.request.LoginRequest
import com.example.api.models.request.SignUpRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import javax.xml.ws.Response
import kotlin.random.Random

class ConduitClientTests {

    val api = ConduitClient.basicApi
    val authApi = ConduitClient.authApi

    @Test
    fun `GET articles`(){
        runBlocking {
            val articles = authApi.getArticles(author = "aman")
            assertNotNull(articles.body()?.articles)
        }
    }

    @Test
    fun `POST signup request`() =
        runBlocking {
            val signUpCreds = SignUpCreds(
                    "test_${Random.nextInt(22,333)}",
                    "testemail${Random.nextInt(22,333)}@test.com",
                    "testPass${Random.nextInt(10000000,999999999)}"
            )
            val user = api.signupUser(SignUpRequest(signUpCreds))
            assertNotNull(user.body()?.user)
        }

    @Test
    fun `POST login request`(){
        runBlocking {
            val signUpCreds = SignUpCreds(
                    "test_${Random.nextInt(22,333)}",
                    "testemail${Random.nextInt(22,333)}@test.com",
                    "testPass${Random.nextInt(10000000,999999999)}"
            )

            api.signupUser(SignUpRequest(signUpCreds))

            val loginCreds = LoginCreds(signUpCreds.email,signUpCreds.password)

            val user = api.loginUser(LoginRequest(loginCreds))

            assertNotNull(user.body()?.user)
        }
    }

    @Test
    fun `GET tags`(){
        runBlocking {
            assertNotNull(api.getTags().body()?.tags)
        }
    }

    @Test
    fun `GET feed`(){
        runBlocking {

            val signUpCreds = SignUpCreds(
                "test_${Random.nextInt(22,333)}",
                "testemail${Random.nextInt(22,333)}@test.com",
                "testPass${Random.nextInt(10000000,999999999)}"
            )

            val userResponse = api.signupUser(SignUpRequest(signUpCreds))

            ConduitClient.authToken = userResponse.body()?.user?.token

            val articles = authApi.getFeedArticles().body()?.articles

            assertNotNull(articles)
        }
    }

}