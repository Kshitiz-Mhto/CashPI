package com.safespend.cashsentry.data.local_data_source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.safespend.cashsentry.data.local_data_source.model.HistoryModel
import com.safespend.cashsentry.data.local_data_source.model.MoneyCardModel
import com.safespend.cashsentry.data.local_data_source.model.UserProfile
import com.safespend.cashsentry.data.local_data_source.repository.history.HistoryRepostory
import com.safespend.cashsentry.data.local_data_source.repository.moneycard.MoneycardRepository
import com.safespend.cashsentry.data.local_data_source.repository.userprofile.UserRepository

@Database(entities = [UserProfile::class, HistoryModel::class, MoneyCardModel::class], version = 1)
abstract class CashSentryDB: RoomDatabase() {

    abstract fun userRepository(): UserRepository
    abstract fun historyRepository(): HistoryRepostory
    abstract fun moneycardRepository(): MoneycardRepository

    companion object {
        @Volatile
        private var INSTANCE: CashSentryDB? = null

        fun getDB(context: Context): CashSentryDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CashSentryDB::class.java,
                        "cashsentryDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }

}