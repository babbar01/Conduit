package com.example.conduit.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.conduit.R
import com.example.conduit.databinding.FragmentLoginSignupBinding
import com.example.conduit.ui.AuthViewModel
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginSignupBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginSignupBinding.inflate(inflater)

        binding.usernameEditText.isVisible = false
        binding.submitButton.text = "Login"
        binding.textViewClickHereToLogin.isVisible = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {

            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Enter Login Credentials", Toast.LENGTH_SHORT).show()
            } else {

                binding.loadingCircular.visibility = View.VISIBLE
                val loginDeffered = authViewModel.login(email, password)

                lifecycleScope.launch {

                    val userResponse = loginDeffered.await()
                    binding.loadingCircular.visibility = View.GONE

                    if (userResponse?.user != null) {
                        Toast.makeText(context, "User Logged In", Toast.LENGTH_SHORT).show()
                        activity?.findNavController(R.id.nav_host_fragment)?.navigateUp()
                    }
                    // TODO: 21-04-2021 handle error case
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}