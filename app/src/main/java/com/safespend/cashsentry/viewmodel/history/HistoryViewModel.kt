package com.safespend.cashsentry.viewmodel.history

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safespend.cashsentry.data.local_data_source.database.CashSentryDB
import com.safespend.cashsentry.data.local_data_source.model.HistoryModel
import com.safespend.cashsentry.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(application: Context): ViewModel() {

    var database: CashSentryDB = CashSentryDB.getDB(application)

    init {
        getHistoryDemo()
    }

    private val _userHistory = MutableStateFlow(HistoryState())
    var userHistory: StateFlow<HistoryState> = _userHistory

    fun getHistoryDemo() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfHistory: List<HistoryModel> = listOf(
                HistoryModel(
                    3,
                    "You have deposited $400 tdoay in your Master Card",
                    true,
                    false,
                    400
                ),
                HistoryModel(
                    33,
                    "You have withdrawl $40 tdoay in your Master Card",
                    false,
                    true,
                    40
                ),
                HistoryModel(
                    32,
                    "You have deposited $90 tdoay in your Master Card",
                    true,
                    false,
                    90
                )
            )
            listOfHistory.forEach{ database.historyRepository().upsertHistory(it)}
            _userHistory.value = HistoryState(
                userHistory = listOfHistory
            )
        }
    }

}