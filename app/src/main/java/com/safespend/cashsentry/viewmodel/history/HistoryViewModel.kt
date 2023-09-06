package com.safespend.cashsentry.viewmodel.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safespend.cashsentry.data.local_data_source.database.CashSentryDB
import com.safespend.cashsentry.data.local_data_source.model.HistoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(application: Context): ViewModel() {

    var database: CashSentryDB = CashSentryDB.getDB(application)

    private val _userHistory = MutableStateFlow(HistoryState())
    var userHistory: StateFlow<HistoryState> = _userHistory

    fun getHistoryDemo() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfHistory = database.historyRepository().getUserWithHistory()
            listOfHistory.collect{
                _userHistory.value = HistoryState(
                    userHistory = it
                )
            }
        }
    }

}