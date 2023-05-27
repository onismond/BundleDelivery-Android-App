package com.bundlegh.bundledelivery.data.network.responses

import com.bundlegh.bundledelivery.data.db.entities.Order
import com.bundlegh.bundledelivery.data.db.entities.Profile

data class ProfileResponse(
    val isSuccessful: Boolean?,
    var profile: Profile,
)
