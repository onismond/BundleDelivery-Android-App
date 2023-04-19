package com.pietech.bundledelivery.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Payout(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var amount: String? = null,
    var date: String? = null,
)