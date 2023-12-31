package com.safespend.cashsentry.data.local_data_source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.safespend.cashsentry.util.Constants

@Entity(tableName = "history")
data class HistoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String = "",
    val isCreated: Boolean = false,
    val isDeleted: Boolean = false,
    val isUpdated: Boolean = false,
    @ColumnInfo("serial")  val serialNum: String = "",
    val amt: Long,
    @ColumnInfo("email") val email: String = ""
)
