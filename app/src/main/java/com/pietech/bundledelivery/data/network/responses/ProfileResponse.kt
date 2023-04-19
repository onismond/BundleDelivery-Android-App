package com.pietech.bundledelivery.data.network.responses

import com.pietech.bundledelivery.data.db.entities.Order
import com.pietech.bundledelivery.data.db.entities.Profile

data class ProfileResponse(
    val isSuccessful: Boolean?,
    var profile: Profile,
)
