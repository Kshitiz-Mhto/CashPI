package com.safespend.cashsentry.data.local_data_source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.safespend.cashsentry.util.Constants
import kotlin.random.Random

@Entity(tableName = "moneycard")
data class MoneyCardModel(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val totalAmt: String,
    val serialNum: String = "****    ****    ****    " + generateRandomDigits().toString(),
    @ColumnInfo("email") val email: String = Constants.ADMIN_EMAIL
)

fun generateRandomDigits() =  Random.nextInt(1000, 10000)