package com.pietech.bundledelivery.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Transaction(
    var id: String? = null,
    var name: String? = null,
    var amount: String? = null,
    var code: String? = null,
    var products: MutableList<Product>? = null,
)