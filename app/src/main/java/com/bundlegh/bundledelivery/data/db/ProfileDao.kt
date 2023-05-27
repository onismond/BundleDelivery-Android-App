package com.bundlegh.bundledelivery.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bundlegh.bundledelivery.data.db.entities.CURRENT_PROFILE_ID
import com.bundlegh.bundledelivery.data.db.entities.CURRENT_USER_ID
import com.bundlegh.bundledelivery.data.db.entities.Profile
import com.bundlegh.bundledelivery.data.db.entities.User

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(profile: Profile): Long

    @Query("SELECT * FROM profile WHERE uid = $CURRENT_PROFILE_ID")
    fun getProfile() : LiveData<Profile>

    @Query("SELECT * FROM profile WHERE uid = $CURRENT_PROFILE_ID")
    fun getCurrentProfile() : Profile

}