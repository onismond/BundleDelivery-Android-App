package com.bundlegh.bundledelivery.data.repositories

import com.bundlegh.bundledelivery.data.db.AppDatabase
import com.bundlegh.bundledelivery.data.db.entities.User
import com.bundlegh.bundledelivery.data.network.MyApi
import com.bundlegh.bundledelivery.data.network.SafeApiRequest
import com.bundlegh.bundledelivery.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    suspend fun userLogin(email: String, password: String) : AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userSignup(
        name: String,
        username: String,
        email: String,
        password: String
    ): AuthResponse {
        return apiRequest { api.userSignup(name, username, email, password) }
    }

    fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()
}