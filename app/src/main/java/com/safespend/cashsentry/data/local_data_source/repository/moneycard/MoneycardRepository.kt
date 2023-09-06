package com.safespend.cashsentry.data.local_data_source.repository.moneycard

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.safespend.cashsentry.data.local_data_source.model.MoneyCardModel
import com.safespend.cashsentry.data.local_data_source.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneycardRepository {

    @Insert
    suspend fun insertMoneycard(moneycard: MoneyCardModel)

    @Update
    suspend fun updateMoneycard(moneycard: MoneyCardModel)

    @Delete
    suspend fun deleteMoneycard(moneycard: MoneyCardModel)

    @Transaction
    @Query("SELECT * FROM moneycard")
    fun getuserWithMoneycard(): Flow<List<MoneyCardModel>>

    @Query("SELECT * FROM user_profile")
    fun getUserData(): Flow<UserProfile>

}