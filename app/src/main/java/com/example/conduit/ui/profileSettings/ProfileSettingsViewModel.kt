package com.example.conduit.ui.profileSettings


import androidx.lifecycle.ViewModel
import com.example.api.ConduitClient
import com.example.api.models.entity.UpdateUserCreds
import com.example.api.models.request.UserUpdateRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ProfileSettingsViewModel : ViewModel() {

    private val TAG = "profileSettingsViewModel"
    private val authApi = ConduitClient.authApi

    fun updateProfile(bio: String?, email: String?, username: String?,newPassword : String?) =
        GlobalScope.async{
            return@async authApi.updateUser(
                UserUpdateRequest(
                UpdateUserCreds(
                    bio,
                    email,
                    null,
                    username,
                    newPassword
                )
            )
        )
    }


}