package com.safespend.cashsentry.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.safespend.cashsentry.databinding.FragmentProfileBinding
import com.safespend.cashsentry.viewmodel.profile.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileViewModel = ViewModelProvider.AndroidViewModelFactory(application = Application()).create(
            ProfileViewModel::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.adminProfile.collect{state ->
                if (state.isLoading) {
                    Log.i("UI load", "lol")
                }else if(state.error.isNotEmpty()){
                    Log.i("UI error", "lol")
                }else if(state.userProfile != null){
                    binding.etUserEmail.text = state.userProfile.email
                    binding.etUserName.text = state.userProfile.name
                }
            }
        }

        return binding.root
    }

}