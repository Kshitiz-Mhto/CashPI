package com.safespend.cashsentry.viewmodel.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safespend.cashsentry.data.local_data_source.database.CashSentryDB
import com.safespend.cashsentry.data.local_data_source.model.UserProfile
import com.safespend.cashsentry.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Context): ViewModel() {

    var database: CashSentryDB = CashSentryDB.getDB(application)
    private lateinit var admin: UserProfile

    init {
        getAdmin()
    }

    private val _userProfile = MutableStateFlow(UserProfileState())
    var adminProfile: StateFlow<UserProfileState> = _userProfile

    fun getAdmin(){
        viewModelScope.launch (Dispatchers.IO){
            admin = database.userRepository().getAdmin(Constants.ADMIN_EMAIL)
            Log.i("data1", admin.email)
            _userProfile.value= UserProfileState(userProfile = admin)
            Log.i("data", _userProfile.value.userProfile!!.email)
        }
    }
}

