package com.pietech.bundledelivery.data.network.responses

import com.pietech.bundledelivery.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val token: String?,
    val user: User?,
)