package com.safespend.cashsentry.data.local_data_source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moneycard")
data class MoneyCardModel(
    val name: String,
    val totalAmt: String,
    @ColumnInfo("email") val email: String,
    @PrimaryKey(autoGenerate = false)
    val serialNum: String = ""
)
