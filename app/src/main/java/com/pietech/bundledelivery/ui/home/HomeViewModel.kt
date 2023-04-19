package com.pietech.bundledelivery.ui.home

import androidx.lifecycle.ViewModel
import com.pietech.bundledelivery.data.db.entities.Order
import com.pietech.bundledelivery.data.db.entities.Payout
import com.pietech.bundledelivery.data.db.entities.Profile
import com.pietech.bundledelivery.data.repositories.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    fun getUserToken() = repository.getUserToken()

    fun getProfile() = repository.getProfile()

    fun saveProfile(profile: Profile) = repository.saveProfile(profile)

    suspend fun loadProfile() = withContext(Dispatchers.IO){
        repository.loadProfile(getUserToken())
    }

    fun getAvailableOrders() = repository.getAvailableOrders()

    fun getOngoingOrders() = repository.getOngoingOrders()

    fun getCompletedOrders() = repository.getCompletedOrders()

    fun getOrder(order_id: String) = repository.getOrder(order_id)

    fun saveOrder(order: Order) = repository.saveOrder(order)

    fun setOngoing(order_id: String) = repository.setOngoing(order_id)

    fun setCompleted(order_id: String) = repository.setCompleted(order_id)

    suspend fun getNewOrders() = withContext(Dispatchers.IO){
        repository.getNewOrders(getUserToken())
    }

    suspend fun getTransactions(tran_ids: String) = withContext(Dispatchers.IO){
        repository.getTransactions(getUserToken(), tran_ids)
    }

    suspend fun acceptOrder(order_id: String, amount: String) = withContext(Dispatchers.IO){
        repository.acceptOrder(getUserToken(), order_id, amount)
    }

    suspend fun completedOrder(order_id: String) = withContext(Dispatchers.IO){
        repository.completedOrder(getUserToken(), order_id)
    }

    fun getPayouts() = repository.getPayouts()

    fun savePayout(payout: Payout) = repository.savePayout(payout)

    suspend fun loadPayouts() = withContext(Dispatchers.IO){
        repository.loadPayouts(getUserToken())
    }

}