package com.safespend.cashsentry.viewmodel.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.safespend.cashsentry.data.local_data_source.database.CashSentryDB
import com.safespend.cashsentry.data.local_data_source.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    var database: CashSentryDB = CashSentryDB.getDB(application)
    private lateinit var admin: UserProfile

    init {
        getAdmin()
    }

    private val _userProfile = MutableStateFlow(UserProfileState())
    var adminProfile: StateFlow<UserProfileState> = _userProfile

    fun getAdmin(){
        viewModelScope.launch (Dispatchers.IO){
            admin = database.userRepository().getAdmin("kalu@gmail.com")
            Log.i("data1", admin.email)
            _userProfile.value= UserProfileState(userProfile = admin)
            Log.i("data", _userProfile.value.userProfile!!.email)
        }
    }
}

