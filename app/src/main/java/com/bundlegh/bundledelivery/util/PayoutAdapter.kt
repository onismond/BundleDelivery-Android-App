package com.bundlegh.bundledelivery.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bundlegh.bundledelivery.R
import com.bundlegh.bundledelivery.data.db.entities.Payout
import kotlinx.android.synthetic.main.item_payout.view.*

class PayoutAdapter(
    private val payouts: MutableList<Payout>,
): RecyclerView.Adapter<PayoutAdapter.OrderNewViewHolder>() {

    inner class OrderNewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderNewViewHolder {
        return OrderNewViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_payout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderNewViewHolder, position: Int) {
        val curItem = payouts[position]
        holder.itemView.apply {
            text_view_id.text = curItem.id
            text_view_amount.text = curItem.amount
            text_view_date.text = curItem.date
        }
    }

    override fun getItemCount(): Int {
        return payouts.size
    }
}