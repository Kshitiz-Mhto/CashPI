package com.safespend.cashsentry.viewmodel.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.safespend.cashsentry.viewmodel.wallet.HomeViewModel

class ProfileViewModelFactory(val application: Context): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(application) as T
    }
}