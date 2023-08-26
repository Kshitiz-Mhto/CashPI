package com.safespend.cashsentry.viewmodel.profile

import com.safespend.cashsentry.data.local_data_source.model.UserProfile

data class UserProfileState(
    val isLoading: Boolean = false,
    val error: String = "",
    val userProfile: UserProfile? = null
)
