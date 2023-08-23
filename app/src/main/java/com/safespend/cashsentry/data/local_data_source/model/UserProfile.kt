package com.safespend.cashsentry.data.local_data_source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    @ColumnInfo("email")
    val email: String,
    @ColumnInfo("name")
    val name: String
)
