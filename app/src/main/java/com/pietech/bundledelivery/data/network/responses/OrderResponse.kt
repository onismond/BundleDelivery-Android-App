package com.pietech.bundledelivery.data.network.responses

import com.pietech.bundledelivery.data.db.entities.Order

data class OrderResponse(
    val isSuccessful: Boolean?,
    val orders: MutableList<Order>,
//    val id: String,
//    val name: String,
//    val amount: String,
//    val status: String,
)
