package com.pietech.bundledelivery.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String? = null,
    var period: String? = null,
    var amount: String? = null,
    var status: String? = null,
    var date_completed: String? = null,
    var transactions: String? = null,
)