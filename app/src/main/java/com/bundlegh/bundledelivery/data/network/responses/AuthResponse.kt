package com.bundlegh.bundledelivery.data.network.responses

import com.bundlegh.bundledelivery.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val token: String?,
    val user: User?,
)