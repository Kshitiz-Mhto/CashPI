package com.safespend.cashsentry.viewmodel.wallet

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safespend.cashsentry.data.local_data_source.database.CashSentryDB
import com.safespend.cashsentry.data.local_data_source.model.HistoryModel
import com.safespend.cashsentry.data.local_data_source.model.MoneyCardModel
import com.safespend.cashsentry.viewmodel.profile.UserProfileState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Context): ViewModel() {

    var database: CashSentryDB = CashSentryDB.getDB(application)
    private val _userWallet = MutableStateFlow(WalletState())
    var userWallet: StateFlow<WalletState> = _userWallet

    private val _userData = MutableStateFlow(UserProfileState())
    var userData: StateFlow<UserProfileState> = _userData

    private val _insertSuccessEvent = MutableLiveData<Boolean>()
    val insertSuccessEvent: LiveData<Boolean> = _insertSuccessEvent

    private val _updateSuccessEvent = MutableLiveData<Boolean>()
    val updateSuccessEvent: LiveData<Boolean> = _updateSuccessEvent

    private val _deleteSuccessEvent = MutableLiveData<Boolean>()
    val deleteSuccessEvent: LiveData<Boolean> = _deleteSuccessEvent


    fun getWalletDemo() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfWallet: Flow<List<MoneyCardModel>> = database.moneycardRepository().getuserWithMoneycard()
            listOfWallet.collect{
                _userWallet.value = WalletState(userWallet = it)
            }
        }
    }

    fun insertWallet(moneyCardModel: MoneyCardModel){
        viewModelScope.launch(Dispatchers.IO) {
            database.moneycardRepository().insertMoneycard(moneyCardModel)
            database.historyRepository().upsertHistory(
                HistoryModel(
                    content = "",
                    isCreated = true,
                    isDeleted = false,
                    isUpdated = false,
                    serialNum = moneyCardModel.serialNum,
                    amt = moneyCardModel.totalAmt.toLong(),
                    email = moneyCardModel.email
                )
            )
            _insertSuccessEvent.postValue(true)
        }
    }

    fun updateWallet(moneyCardModel: MoneyCardModel){
        viewModelScope.launch(Dispatchers.IO) {
            database.moneycardRepository().updateMoneycard(moneyCardModel)
            database.historyRepository().upsertHistory(
                HistoryModel(
                    content = "",
                    isCreated = false,
                    isDeleted = false,
                    isUpdated = true,
                    serialNum = moneyCardModel.serialNum,
                    amt = moneyCardModel.totalAmt.toLong(),
                    email = moneyCardModel.email
                )
            )
            _updateSuccessEvent.postValue(true)
        }
    }

    fun deleteWallet(moneyCardModel: MoneyCardModel){
        viewModelScope.launch(Dispatchers.IO) {
            database.moneycardRepository().deleteMoneycard(moneyCardModel)
            database.historyRepository().upsertHistory(
                HistoryModel(
                    content = "",
                    isCreated = false,
                    isDeleted = true,
                    isUpdated = false,
                    serialNum = moneyCardModel.serialNum,
                    amt = moneyCardModel.totalAmt.toLong(),
                    email = moneyCardModel.email
                )
            )
            _deleteSuccessEvent.postValue(true)
        }
    }

    fun getUserData(){
        viewModelScope.launch(Dispatchers.IO){
            val user = database.moneycardRepository().getUserData()
            user.collect{
                _userData.value = UserProfileState(userProfile = it)
            }
        }
    }

}