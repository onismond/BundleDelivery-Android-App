package com.bundlegh.bundledelivery.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bundlegh.bundledelivery.data.db.entities.Order
import com.bundlegh.bundledelivery.data.db.entities.Payout
import com.bundlegh.bundledelivery.data.db.entities.Profile
import com.bundlegh.bundledelivery.data.db.entities.User
import com.bundlegh.bundledelivery.util.Converters

@Database(
    entities = [User::class, Order::class, Profile::class, Payout::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao() : UserDao
    abstract fun getOrderDao() : OrderDao
    abstract fun getProfileDao() : ProfileDao
    abstract fun getPayoutDao() : PayoutDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "BundleDeliveryDatabase.db"
            ).build()

    }
}