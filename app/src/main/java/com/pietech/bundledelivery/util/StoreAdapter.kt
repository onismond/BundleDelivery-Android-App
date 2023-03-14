package com.pietech.bundledelivery.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pietech.bundledelivery.R
import com.pietech.bundledelivery.data.db.entities.OrderStore
import kotlinx.android.synthetic.main.item_store.view.*

class StoreAdapter(
    private val stores: MutableList<OrderStore>
): RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private var viewPool = RecyclerView.RecycledViewPool()
//    lateinit var onOrderClicked: ((Order) -> Unit)

    inner class StoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val text_view_name: TextView = itemView.text_view_name
        init {
//            itemView.setOnClickListener{
////                onOrderClicked.invoke(orders[bindingAdapterPosition])
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_store,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val curItem = stores[position]
        holder.itemView.apply {
            text_view_store_name.text = curItem.name
            text_view_amount.text = storeTotal(curItem)
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
        return stores.size
    }

    private fun storeTotal(store: OrderStore): String {
        return "10.00"
    }
}