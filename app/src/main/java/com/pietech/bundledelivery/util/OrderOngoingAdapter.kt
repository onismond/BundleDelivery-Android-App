package com.pietech.bundledelivery.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pietech.bundledelivery.R
import com.pietech.bundledelivery.data.db.entities.Order
import com.pietech.bundledelivery.data.db.entities.OrderStore
import com.pietech.bundledelivery.data.db.entities.Store
import kotlinx.android.synthetic.main.item_order_ongoing.view.*

class OrderOngoingAdapter(
    private val orders: MutableList<Order>,
    private val orderStores: MutableList<OrderStore>
): RecyclerView.Adapter<OrderOngoingAdapter.OrderOngoingViewHolder>() {

    private var viewPool = RecyclerView.RecycledViewPool()
//    lateinit var onOrderClicked: ((Order) -> Unit)

    inner class OrderOngoingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val text_view_name: TextView = itemView.text_view_name
        init {
//            itemView.setOnClickListener{
////                onOrderClicked.invoke(orders[bindingAdapterPosition])
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderOngoingViewHolder {
        return OrderOngoingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_order_ongoing,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderOngoingViewHolder, position: Int) {
        val curItem = orders[position]
        holder.itemView.apply {
            text_view_name.text = curItem.name
            text_view_amount.text = curItem.amount
        }
//        val stores = mutableListOf(Store("1", "Joe's Kebab"), Store("2", "Bread store"))
//        val storeAdapter = StoreAdapter(stores)
//        val linearLayoutManager = object: LinearLayoutManager(holder.itemView.recycler_view_store.context) { override fun canScrollVertically() = false}
//        linearLayoutManager.initialPrefetchItemCount = 2
//        holder.itemView.recycler_view_store.apply {
//            adapter = storeAdapter
//            layoutManager = linearLayoutManager
//            setRecycledViewPool(viewPool)
//        }
        if (curItem.order_stores != null){

            val storeAdapter = StoreAdapter(orderStores)
            val linearLayoutManager = object: LinearLayoutManager(holder.itemView.recycler_view_store.context) { override fun canScrollVertically() = false}
            linearLayoutManager.initialPrefetchItemCount = orderStores.size
            holder.itemView.recycler_view_store.apply {
                adapter = storeAdapter
                layoutManager = linearLayoutManager
                setRecycledViewPool(viewPool)
            }
        } else {
            println("Empty")
            println("Empty")
            println("Empty")
            println(curItem)
        }


    }

    override fun getItemCount(): Int {
        return orders.size
    }
}

