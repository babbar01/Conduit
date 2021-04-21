package com.example.api

import com.example.api.services.ConduitApi
import com.example.api.services.ConduitAuthApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ConduitClient {

    var authToken : String? = null
    private var authIntercepter = Interceptor {chain->

        var request = chain.request()

        request = authToken.let {
            request.newBuilder()
                .addHeader("Authorization","Token $it")
                .build()
        }

        return@Interceptor chain.proceed(request)
    }


    private val okkHttpBuilder = OkHttpClient.Builder()
        .readTimeout(5,TimeUnit.SECONDS)
        .callTimeout(5,TimeUnit.SECONDS)
        .connectTimeout(5,TimeUnit.SECONDS)

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://conduit.productionready.io/api/")
        .addConverterFactory(MoshiConverterFactory.create())

    val basicApi = retrofitBuilder
        .client(okkHttpBuilder.build())
        .build().
        create(ConduitApi::class.java)!!

    val authApi = retrofitBuilder
        .client(okkHttpBuilder.addInterceptor(authIntercepter).build())
        .build()
        .create(ConduitAuthApi::class.java)!!

}