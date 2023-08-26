package com.safespend.cashsentry.data.local_data_source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "moneycard")
data class MoneyCardModel(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val totalAmt: String,
    @ColumnInfo("email") val email: String,
    val serialNum: String = "****    ****    ****    " + generateRandomDigits().toString()
)

fun generateRandomDigits() =  Random.nextInt(1000, 10000)