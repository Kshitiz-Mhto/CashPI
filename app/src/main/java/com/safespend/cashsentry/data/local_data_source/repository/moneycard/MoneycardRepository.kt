package com.safespend.cashsentry.data.local_data_source.repository.moneycard

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.safespend.cashsentry.data.local_data_source.model.MoneyCardModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneycardRepository {

    @Upsert
    suspend fun upsertMoneycard(moneycard: MoneyCardModel)

    @Transaction
    @Query("SELECT * FROM moneycard WHERE email = :email")
    fun getuserWithMoneycard(email: String): Flow<List<MoneyCardModel>>

}