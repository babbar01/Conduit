package com.example.conduit.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.api.ConduitClient
import com.example.conduit.R
import com.example.conduit.databinding.FragmentProfileBinding
import com.example.conduit.ui.AuthViewModel
import com.google.android.material.tabs.TabLayout

class ProfileFragment : Fragment() {

    val TAG = "glide"

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        authViewModel.user.observe(viewLifecycleOwner) {
            Log.d(TAG, "onCreateView: ${it?.image}")
            binding.profileNameTextView.text = it?.username
            it?.image?.let { url -> Glide.with(this).load(url).into(binding.profileImageView) }
        }

        binding.chipEditSettings.setOnClickListener{
            activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.action_navigation_profile_to_profileSettings)
        }

        settingTabLayoutListener()

        return binding.root
    }

    private fun settingTabLayoutListener() {
        val navController =
            Navigation.findNavController(binding.root.findViewById(R.id.profile_nav_host_fragment))
        binding.profileFragmentTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> if (navController.currentDestination?.id == R.id.fragmentFavouritedArticles)
                        navController.navigate(
                            R.id.action_fragmentFavouritedArticles_to_fragmentMyArticles
                        )
                    1 -> if (navController.currentDestination?.id == R.id.fragmentMyArticles)
                        navController.navigate(
                        R.id.action_fragmentMyArticles_to_fragmentFavouritedArticles
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logout) {
            authViewModel.user.value = null
            ConduitClient.authToken = null
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}