package com.bundlegh.bundledelivery.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Product (
        var id: String? = null,
        var name: String? = null,
        var amount: String? = null,
        var description: String? = null,
        var image: String? = null,
        var location: String? = null,
)