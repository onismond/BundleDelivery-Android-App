package com.pietech.bundledelivery.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Store(
    @PrimaryKey(autoGenerate = false)
    var id: String? = null,
    var name: String? = null,
    var products: MutableList<Product>? = null,
)