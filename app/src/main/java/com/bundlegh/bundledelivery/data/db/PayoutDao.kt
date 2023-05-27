package com.bundlegh.bundledelivery.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bundlegh.bundledelivery.data.db.entities.Payout

@Dao
interface PayoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(payout: Payout): Long

    @Query("SELECT * FROM payout ORDER BY id DESC")
    fun getPayouts() : LiveData<MutableList<Payout>>

}