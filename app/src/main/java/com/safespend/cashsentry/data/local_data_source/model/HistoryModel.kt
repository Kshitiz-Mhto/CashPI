package com.safespend.cashsentry.data.local_data_source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.safespend.cashsentry.util.Constants

@Entity(tableName = "history")
data class HistoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val content: String = "",
    val isDesposit: Boolean = false,
    val isWithdrawl: Boolean = false,
    val amt: Long,
    @ColumnInfo("email") val email: String = ""
)
