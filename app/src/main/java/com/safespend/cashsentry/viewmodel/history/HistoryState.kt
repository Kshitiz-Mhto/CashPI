package com.safespend.cashsentry.viewmodel.history

import com.safespend.cashsentry.data.local_data_source.model.HistoryModel
import com.safespend.cashsentry.data.local_data_source.model.UserProfile

data class HistoryState (
    val isLoading: Boolean = false,
    val error: String = "",
    val userHistory: List<HistoryModel>? = null
)
