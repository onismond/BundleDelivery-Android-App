package com.bundlegh.bundledelivery.data.network.responses

import com.bundlegh.bundledelivery.data.db.entities.Payout

data class PayoutResponse(
    val isSuccessful: Boolean?,
    var payouts: MutableList<Payout>?,
)
