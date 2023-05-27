package com.bundlegh.bundledelivery.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bundlegh.bundledelivery.R
import com.bundlegh.bundledelivery.data.db.entities.Order
import kotlinx.android.synthetic.main.item_order_completed.view.*

class OrderCompletedAdapter(
    private val orders: MutableList<Order>
): RecyclerView.Adapter<OrderCompletedAdapter.OrderCompletedViewHolder>() {

//    lateinit var onOrderClicked: ((Order) -> Unit)

    inner class OrderCompletedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val text_view_name: TextView = itemView.text_view_name
        init {
//            itemView.setOnClickListener{
////                onOrderClicked.invoke(orders[bindingAdapterPosition])
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderCompletedViewHolder {
        return OrderCompletedViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_order_completed,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderCompletedViewHolder, position: Int) {
        val curItem = orders[position]
        holder.itemView.apply {
            text_view_name.text = curItem.name
            text_view_amount.text = curItem.amount
            text_view_date_completed.text = curItem.date_completed
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}