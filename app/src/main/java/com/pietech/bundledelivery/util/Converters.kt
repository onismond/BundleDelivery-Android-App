package com.pietech.bundledelivery.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pietech.bundledelivery.data.db.entities.Product
import com.pietech.bundledelivery.data.db.entities.Profile

class Converters {
//    @TypeConverter
//    fun productListToJson(value: MutableList<Product>): String{
//        return Gson().toJson(value)
//    }

    @TypeConverter
    fun StoreListToJson(value: MutableList<Profile>): String{
        return Gson().toJson(value)
    }

//    @TypeConverter
//    fun jsonToProductList(value:String): MutableList<Product> {
//        val type = object : TypeToken<MutableList<Product>?>() {}.type
//        return Gson().fromJson(value, type)
//    }

    @TypeConverter
    fun jsonToStoreList(value:String): MutableList<Product> {
        val type = object : TypeToken<MutableList<Product>?>() {}.type
        return Gson().fromJson(value, type)
    }

}