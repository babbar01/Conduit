package com.example.conduit.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.api.ConduitClient
import com.example.api.models.entity.LoginCreds
import com.example.api.models.entity.SignUpCreds
import com.example.api.models.entity.User
import com.example.api.models.request.LoginRequest
import com.example.api.models.request.SignUpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
class AuthViewModel : ViewModel() {

    val TAG = "AuthViewModelTag"

    val basicApi = ConduitClient.basicApi

    val user = MutableLiveData<User?>(null)

    fun login(email: String, password: String) = viewModelScope.async(Dispatchers.IO) {
        val userResponse = basicApi.loginUser(LoginRequest(LoginCreds(email, password)))
        userResponse.body()?.let {
            user.postValue(it.user)
            ConduitClient.authToken = it.user.token
        }

        return@async userResponse.body()
    }

    fun signup(username: String, email: String, password: String) =
        viewModelScope.async(Dispatchers.IO) {
            val response =
                basicApi.signupUser(SignUpRequest(SignUpCreds(username, email, password)))

            response.body()?.let {

                val muser = it.user
                Log.d(TAG, "signup: user ${muser.username}")

                user.postValue(muser)
                ConduitClient.authToken = muser.token
            }

            return@async response
        }


}



