package com.safespend.cashsentry.viewmodel.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safespend.cashsentry.data.local_data_source.database.CashSentryDB
import com.safespend.cashsentry.data.local_data_source.model.UserProfile
import com.safespend.cashsentry.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Context): ViewModel() {

    var database: CashSentryDB = CashSentryDB.getDB(application)

    private val _userProfile = MutableStateFlow(UserProfileState())
    var adminProfile: StateFlow<UserProfileState> = _userProfile

    private val _upsertSuccessEvent = MutableLiveData<Boolean>()
    var upsertSuccessEvent: LiveData<Boolean> = _upsertSuccessEvent

    private val _deleteSuccessEvent = MutableLiveData<Boolean>(false)
    var deleteSuccessEvent: LiveData<Boolean> = _deleteSuccessEvent

    fun getAdmin(){
        viewModelScope.launch (Dispatchers.IO){
            val admin: UserProfile = database.userRepository().getAdmin(Constants.ADMIN_EMAIL)
            _userProfile.value= UserProfileState(userProfile = admin)
        }
    }

    fun deletePreviousAdmin(previousEmail: String){
        viewModelScope.launch(Dispatchers.IO) {
            database.userRepository().delupdateUser(previousEmail)
            _deleteSuccessEvent.postValue(true)
        }
    }

    fun upsertAdmin(adminProfile: UserProfile){
        Constants.ADMIN_EMAIL = adminProfile.email
        viewModelScope.launch(Dispatchers.IO){
            database.userRepository().upsertUser(adminProfile)
            _upsertSuccessEvent.postValue(true)
        }
    }
}

