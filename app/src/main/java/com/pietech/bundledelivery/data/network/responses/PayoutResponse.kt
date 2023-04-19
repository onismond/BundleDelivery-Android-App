package com.pietech.bundledelivery.data.network.responses

import com.pietech.bundledelivery.data.db.entities.Payout

data class PayoutResponse(
    val isSuccessful: Boolean?,
    var payouts: MutableList<Payout>?,
)
