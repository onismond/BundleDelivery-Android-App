package com.pietech.bundledelivery.data.network.responses

import com.pietech.bundledelivery.data.db.entities.Order
import com.pietech.bundledelivery.data.db.entities.Transaction

data class TransactionResponse(
    val isSuccessful: Boolean?,
    val transactions: MutableList<Transaction>,
)
