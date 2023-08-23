package com.safespend.cashsentry.data.local_data_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val email: String,
    val name: String
)
