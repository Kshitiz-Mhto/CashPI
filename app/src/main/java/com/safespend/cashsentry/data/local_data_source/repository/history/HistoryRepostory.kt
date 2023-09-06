package com.safespend.cashsentry.data.local_data_source.repository.history

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.safespend.cashsentry.data.local_data_source.model.HistoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryRepostory {

    @Upsert
    suspend fun upsertHistory(historyModel: HistoryModel)

    @Transaction
    @Query("SELECT * FROM history")
    fun getUserWithHistory(): Flow<List<HistoryModel>>

}