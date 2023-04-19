package com.pietech.bundledelivery.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pietech.bundledelivery.R
import com.pietech.bundledelivery.data.db.entities.Order
import com.pietech.bundledelivery.data.db.entities.Transaction
import kotlinx.android.synthetic.main.item_order_ongoing.view.*

class OrderOngoingAdapter(
    private val orders: MutableList<Order>,
    private val transactions: MutableList<Transaction>
): RecyclerView.Adapter<OrderOngoingAdapter.OrderOngoingViewHolder>() {

    private var viewPool = RecyclerView.RecycledViewPool()
    lateinit var onCompletedClicked: ((String) -> Unit)

    inner class OrderOngoingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val buttonCompleted: Button = itemView.button_completed
        init {
            buttonCompleted.setOnClickListener{
                onCompletedClicked.invoke(orders[bindingAdapterPosition].id)
            }
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
        if (curItem.transactions != null){
            val curTrans: MutableList<Transaction> = mutableListOf()
            val curTranList = curItem.transactions?.split(',')
            for (transaction in transactions)
                if (curTranList!!.contains(transaction.id))
                    curTrans.add(transaction)
            val transactionAdapter = TransactionAdapter(curTrans)
            val linearLayoutManager = object: LinearLayoutManager(holder.itemView.recycler_view_store.context) { override fun canScrollVertically() = false}
            linearLayoutManager.initialPrefetchItemCount = transactions.size
            holder.itemView.recycler_view_store.apply {
                adapter = transactionAdapter
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

