package com.bundlegh.bundledelivery.data.network.responses

import com.bundlegh.bundledelivery.data.db.entities.Order
import com.bundlegh.bundledelivery.data.db.entities.Transaction

data class TransactionResponse(
    val isSuccessful: Boolean?,
    val transactions: MutableList<Transaction>,
)
