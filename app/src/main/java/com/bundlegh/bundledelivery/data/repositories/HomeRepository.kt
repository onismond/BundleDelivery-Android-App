package com.bundlegh.bundledelivery.data.repositories

import com.bundlegh.bundledelivery.data.db.AppDatabase
import com.bundlegh.bundledelivery.data.db.entities.Order
import com.bundlegh.bundledelivery.data.db.entities.Payout
import com.bundlegh.bundledelivery.data.db.entities.Profile
import com.bundlegh.bundledelivery.data.network.MyApi
import com.bundlegh.bundledelivery.data.network.SafeApiRequest
import com.bundlegh.bundledelivery.data.network.responses.*

class HomeRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    fun getUserToken() = db.getUserDao().getUserToken() //

    fun getProfile() = db.getProfileDao().getProfile()

    fun saveProfile(profile: Profile) = db.getProfileDao().upsert(profile)

    suspend fun loadProfile(token: String): ProfileResponse {
        return apiRequest { api.loadProfile(token) }
    }

    fun getAvailableOrders() = db.getOrderDao().getAvailableOrders()

    fun getOngoingOrders() = db.getOrderDao().getOngoingOrders()

    fun getCompletedOrders() = db.getOrderDao().getCompletedOrders()

    fun getOrder(order_id: String) = db.getOrderDao().getOrder(order_id)

    fun saveOrder(order: Order) = db.getOrderDao().upsert(order)

    fun setOngoing(order_id: String) = db.getOrderDao().setOngoing(order_id)

    fun setCompleted(order_id: String) = db.getOrderDao().setCompleted(order_id)

    suspend fun getNewOrders(token: String): OrderResponse {
        return apiRequest { api.getNewOrders(token) }
    }

    suspend fun getTransactions(token: String, tran_ids: String): TransactionResponse {
        return apiRequest { api.getTransactions(token, tran_ids) }
    }

    suspend fun acceptOrder(token: String, order_id: String, amount: String): AcceptOrderResponse {
        return apiRequest { api.acceptOrder(token, order_id, amount) }
    }

    suspend fun completedOrder(token: String, order_id: String): AcceptOrderResponse {
        return apiRequest { api.completedOrder(token, order_id) }
    }

    fun getPayouts() = db.getPayoutDao().getPayouts()

    fun savePayout(payout: Payout) = db.getPayoutDao().upsert(payout)

    suspend fun loadPayouts(token: String): PayoutResponse {
        return apiRequest { api.loadPayouts(token) }
    }

}

