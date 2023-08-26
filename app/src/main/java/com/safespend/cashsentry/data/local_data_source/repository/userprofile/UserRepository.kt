package com.safespend.cashsentry.data.local_data_source.repository.userprofile

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.safespend.cashsentry.data.local_data_source.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRepository {

    @Upsert
    suspend fun upsertUser(userProfile: UserProfile)


    @Transaction
    @Query("SELECT * FROM user_profile WHERE email = :adminEmail")
    suspend fun getAdmin(adminEmail: String): UserProfile


    @Transaction
    @Query("DELETE FROM user_profile where email = :email")
    suspend fun delupdateUser(email: String)

}