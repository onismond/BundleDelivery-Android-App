package com.pietech.bundledelivery.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pietech.bundledelivery.R
import com.pietech.bundledelivery.data.db.entities.Order
import com.pietech.bundledelivery.data.db.entities.OrderStore
import com.pietech.bundledelivery.data.db.entities.Product
import com.pietech.bundledelivery.data.db.entities.Store
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(
    private val product: MutableList<Product>
): RecyclerView.Adapter<ProductAdapter.StoreViewHolder>() {

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
                R.layout.item_product,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val curItem = product[position]
        holder.itemView.apply {
            text_view_store_name.text = curItem.name
            text_view_amount.text = curItem.amount
        }

    }

    override fun getItemCount(): Int {
        return product.size
    }
}