package com.pietech.bundledelivery.ui.auth

import androidx.lifecycle.ViewModel
import com.pietech.bundledelivery.data.db.entities.User
import com.pietech.bundledelivery.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: UserRepository
): ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String,
        password: String,
    ) = withContext(Dispatchers.IO){repository.userLogin(email, password)}

    suspend fun userSignup(
        name: String,
        username: String,
        email: String,
        password: String,
    ) = withContext(Dispatchers.IO){repository.userSignup(name, username, email, password)}

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)
}