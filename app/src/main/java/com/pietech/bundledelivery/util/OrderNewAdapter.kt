package com.pietech.bundledelivery.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pietech.bundledelivery.R
import com.pietech.bundledelivery.data.db.entities.Order
import kotlinx.android.synthetic.main.item_order_new.view.*

class OrderNewAdapter(
    private val orders: MutableList<Order>
): RecyclerView.Adapter<OrderNewAdapter.OrderNewViewHolder>() {

    lateinit var onOrderClicked: ((Order) -> Unit)

    inner class OrderNewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                onOrderClicked.invoke(orders[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderNewViewHolder {
        return OrderNewViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_order_new,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderNewViewHolder, position: Int) {
        val curItem = orders[position]
        holder.itemView.apply {
            text_view_name.text = curItem.name
            text_view_amount.text = curItem.amount
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}