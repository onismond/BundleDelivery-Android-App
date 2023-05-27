package com.bundlegh.bundledelivery.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bundlegh.bundledelivery.R
import com.bundlegh.bundledelivery.data.db.entities.Transaction
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionAdapter(
    private val transactions: MutableList<Transaction>
): RecyclerView.Adapter<TransactionAdapter.StoreViewHolder>() {

    private var viewPool = RecyclerView.RecycledViewPool()
//    lateinit var onTransactionClicked: ((Transaction) -> Unit)

    inner class StoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val text_view_name: TextView = itemView.text_view_name
        init {
//            itemView.setOnClickListener{
//                onTransactionClicked.invoke(transactions[bindingAdapterPosition])
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_transaction,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val curItem = transactions[position]
        holder.itemView.apply {
            text_view_store_name.text = curItem.name
            text_view_amount.text = storeTotal(curItem)
            text_view_transaction_code.text = curItem.code
        }
        if (curItem.products != null){
            val productAdapter = ProductAdapter(curItem.products!!)
            val linearLayoutManager = object: LinearLayoutManager(holder.itemView.recycler_view_product.context) { override fun canScrollVertically() = false}
            holder.itemView.recycler_view_product.apply {
                adapter = productAdapter
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
        return transactions.size
    }

    private fun storeTotal(store: Transaction): String {
        return "10.00"
    }
}
