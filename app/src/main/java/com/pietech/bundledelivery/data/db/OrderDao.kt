package com.pietech.bundledelivery.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pietech.bundledelivery.data.db.entities.Order

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(order: Order): Long

    @Query("UPDATE `order` SET status='ongoing' WHERE id = :order_id")
    fun setOngoing(order_id: String): Int

    @Query("UPDATE `order` SET status='completed' WHERE id = :order_id")
    fun setCompleted(order_id: String): Int

    @Query("SELECT * FROM `order` WHERE id= :order_id LIMIT 1")
    fun getOrder(order_id:String) : Order

    @Query("SELECT * FROM `order` WHERE status='pending'")
    fun getAvailableOrders() : LiveData<MutableList<Order>>

    @Query("SELECT * FROM `order` WHERE status='ongoing'")
    fun getOngoingOrders() : LiveData<MutableList<Order>>

    @Query("SELECT * FROM `order` WHERE status='completed'")
    fun getCompletedOrders() : LiveData<MutableList<Order>>

}