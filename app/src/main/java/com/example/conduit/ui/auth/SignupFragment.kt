package com.example.conduit.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.conduit.R
import com.example.conduit.databinding.FragmentLoginSignupBinding
import com.example.conduit.ui.AuthViewModel
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    private var _binding: FragmentLoginSignupBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginSignupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Enter Signup Credentials", Toast.LENGTH_SHORT).show()
            } else {
                binding.loadingCircular.visibility = View.VISIBLE
                val signupDeffered = authViewModel.signup(username, email, password)

                lifecycleScope.launch {
                    val response = signupDeffered.await()
                    binding.loadingCircular.visibility = View.GONE
                    if (response.errorBody() == null) {
                        Toast.makeText(context, "User Logged In", Toast.LENGTH_SHORT).show()
                        activity?.findNavController(R.id.nav_host_fragment)?.navigateUp()
                    } else {
                        // TODO: 21-04-2021 handle this by making proper pojo and properly parse this string
                        Toast.makeText(context, response.errorBody()?.string(), Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }

        binding.textViewClickHereToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}