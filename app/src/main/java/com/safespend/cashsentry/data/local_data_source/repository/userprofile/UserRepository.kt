package com.safespend.cashsentry.data.local_data_source.repository.userprofile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.safespend.cashsentry.data.local_data_source.model.UserProfile

@Dao
interface UserRepository {

    @Upsert
    suspend fun insertContact(userProfile: UserProfile)

    @Delete
    suspend fun deleteContact(userProfile: UserProfile)

}