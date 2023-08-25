package com.safespend.cashsentry.viewmodel.wallet

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safespend.cashsentry.data.local_data_source.database.CashSentryDB
import com.safespend.cashsentry.data.local_data_source.model.MoneyCardModel
import com.safespend.cashsentry.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Context): ViewModel() {

    var database: CashSentryDB = CashSentryDB.getDB(application)

    init {
        getWalletDemo()
    }

    private val _userWallet = MutableStateFlow(WalletState())
    var userWallet: StateFlow<WalletState> = _userWallet

    private val _upsertSuccessEvent = MutableLiveData<Boolean>()
    val upsertSuccessEvent: LiveData<Boolean> = _upsertSuccessEvent


    fun getWalletDemo() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfWallet: Flow<List<MoneyCardModel>> = database.moneycardRepository().getuserWithMoneycard(Constants.ADMIN_EMAIL)
            listOfWallet.collect{
                _userWallet.value = WalletState(userWallet = it)
            }
        }
    }

    fun upsertWallet(moneyCardModel: MoneyCardModel){
        viewModelScope.launch(Dispatchers.IO) {
            database.moneycardRepository().upsertMoneycard(moneyCardModel)
            _upsertSuccessEvent.postValue(true)
        }
    }

}