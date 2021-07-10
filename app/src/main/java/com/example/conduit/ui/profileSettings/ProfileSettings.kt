package com.example.conduit.ui.profileSettings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.api.ConduitClient
import com.example.conduit.R
import com.example.conduit.databinding.FragmentProfileSettingsBinding
import com.example.conduit.ui.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileSettings : Fragment() {

    private var _binding : FragmentProfileSettingsBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()
    private lateinit var profileSettingsViewModel : ProfileSettingsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileSettingsBinding.inflate(inflater,container,false)

        profileSettingsViewModel = ViewModelProvider(this)[ProfileSettingsViewModel::class.java]

        authViewModel.user.observe(viewLifecycleOwner){
            it?.let {
               binding.apply {
                   binding.tvNameProfileSettings.text = it.username
                   editTextUsername.setText(it.username)
                   editTextBio.setText(it.bio)
                   editTextEmail.setText(it.email)
               }
            }
        }

        binding.btnUpdateProfile.setOnClickListener{
            val username = binding.editTextUsername.text.toString()
            val bio = binding.editTextBio.text.toString()
            val email = binding.editTextEmail.text.toString()
            var password : String? = binding.editTextPassword.text.toString()
            if (password!!.isEmpty()) password = null

            val profileUpdateDeferred = profileSettingsViewModel.updateProfile(
                bio,
                email,
                username,
                password
            )

            lifecycleScope.launch {
                val response = profileUpdateDeferred.await()
                if(response.isSuccessful) ConduitClient.authToken?.let { authViewModel.getCurrentUser(it) }
                else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(context, "${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }

        return binding.root
    }
}