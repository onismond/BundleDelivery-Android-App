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

    @Query("SELECT * FROM `order`")
    fun getAvailableOrders() : LiveData<Order>

}