package com.pietech.bundledelivery.data.network

import com.pietech.bundledelivery.data.db.entities.Order

interface OrderListener {
    fun  onConnectSuccess () // successfully connected
    fun  onConnectFailed () // connection failed
    fun  onClose () // close
    fun onOrder(order: Order?)
}
