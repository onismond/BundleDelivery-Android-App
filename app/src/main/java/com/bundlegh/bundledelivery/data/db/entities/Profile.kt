package com.bundlegh.bundledelivery.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_PROFILE_ID = 0

@Entity
data class Profile(
    var account_balance: String? = null,
    var not_cleared_total: String? = null,
    var not_cleared_count: Int? = null,
    var not_cleared_balance: String? = null,
){
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_PROFILE_ID
}