package com.safespend.cashsentry.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.safespend.cashsentry.data.local_data_source.model.MoneyCardModel
import com.safespend.cashsentry.data.local_data_source.model.UserProfile

data class UserWithMoneycard(
    @Embedded val userProfile: UserProfile,
    @Relation(
        parentColumn = "email",
        entityColumn = "email"
    )
    val moneycardModel: List<MoneyCardModel>
)
