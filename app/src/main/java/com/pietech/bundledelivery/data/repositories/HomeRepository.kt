package com.pietech.bundledelivery.data.repositories

import com.pietech.bundledelivery.data.db.AppDatabase
import com.pietech.bundledelivery.data.network.MyApi
import com.pietech.bundledelivery.data.network.SafeApiRequest

class HomeRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {
//
//    suspend fun index() : IndexResponse {
//        return apiRequest { api.index() }
//    }
//
//    fun getUser() = db.getUserDao().getCurrentUser()
//
//    fun saveProduct(product: Product) = db.getCurrentProductDao().upsert(product)
//
//    fun getCurrentProduct() = db.getCurrentProductDao().getproduct()
////    Cart
//    fun addToCart(cart: Cart) = db.getCartDao().upsert(cart)
//
//    fun removeFromCart(cart: Cart) = db.getCartDao().remove(cart)
//
//    fun getCartItems() = db.getCartDao().getcart()
//
//    fun getCurrentCart() = db.getCartDao().getCurrentCart()
////    Orders
//    fun addToOrders(order: Order) = db.getOrderDao().upsert(order)
//
//    fun removeFromOrders(order: Order) = db.getOrderDao().remove(order)
//
//    fun getOrders() = db.getOrderDao().getorder()
//
//    suspend fun getSearchItems(keyword: String) : SearchResponse {
//        return apiRequest { api.search(keyword) }
//    }
//
//    suspend fun submitOrder(token: String, cart: String) : OrderResponse {
//        return apiRequest { api.order(token, cart) }
//    }

}

