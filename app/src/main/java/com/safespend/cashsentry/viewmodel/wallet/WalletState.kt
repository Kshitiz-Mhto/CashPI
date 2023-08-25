package com.safespend.cashsentry.viewmodel.wallet

import com.safespend.cashsentry.data.local_data_source.model.MoneyCardModel

data class WalletState(
    val isLoading: Boolean = false,
    val error: String = "",
    val userWallet: List<MoneyCardModel>? = null
)