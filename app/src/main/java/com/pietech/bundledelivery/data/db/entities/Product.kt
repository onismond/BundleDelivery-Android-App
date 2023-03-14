package com.pietech.bundledelivery.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (
        @PrimaryKey(autoGenerate = false)
        var id: String? = null,
        var name: String? = null,
        var amount: String? = null,
        var description: String? = null,
        var image: String? = null,
        var location: String? = null,
)