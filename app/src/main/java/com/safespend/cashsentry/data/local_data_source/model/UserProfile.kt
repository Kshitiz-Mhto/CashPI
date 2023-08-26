package com.safespend.cashsentry.data.local_data_source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey()
    @NotNull
    @ColumnInfo("email")
    val email: String,
    @ColumnInfo("name")
    val name: String
)
