package com.bundlegh.bundledelivery.util

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bundlegh.bundledelivery.R
import com.bundlegh.bundledelivery.data.db.entities.Order
import com.bundlegh.bundledelivery.data.db.entities.Transaction
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker
import kotlinx.android.synthetic.main.fragment_order.view.*
import kotlinx.android.synthetic.main.item_order_ongoing.view.*
import kotlinx.android.synthetic.main.item_order_ongoing.view.recycler_view_store
import kotlinx.android.synthetic.main.item_order_ongoing.view.text_view_amount
import kotlinx.android.synthetic.main.item_order_ongoing.view.text_view_name

class OrderOngoingAdapter(
    private val orders: MutableList<Order>,
    private val transactions: MutableList<Transaction>
): RecyclerView.Adapter<OrderOngoingAdapter.OrderOngoingViewHolder>() {

    private var viewPool = RecyclerView.RecycledViewPool()
    lateinit var onCompletedClicked: ((String) -> Unit)

    inner class OrderOngoingViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView), OnMapReadyCallback {
        lateinit var order: Order
        val buttonCompleted: Button = itemView.button_completed
        val mapView: CustomMapView = itemView.ongoing_map_view
        var mapReady: Boolean = false
        var dataReady: Boolean = false
        private var marker: Marker? = null
        private val zoom = 17F
        private lateinit var googleMap: GoogleMap
        private val userIcon: BitmapDescriptor by lazy {
            val color = ContextCompat.getColor(mapView.context, R.color.primary)
            BitmapHelper.vectorToBitmap(mapView.context, R.drawable.home_icon, color)
        }
        private val storeIcon: BitmapDescriptor by lazy {
            val color = ContextCompat.getColor(mapView.context, R.color.primary)
            BitmapHelper.vectorToBitmap(mapView.context, R.drawable.add_icon, color)
        }

        init {
            createMapFragment()
            buttonCompleted.setOnClickListener{
                onCompletedClicked.invoke(orders[bindingAdapterPosition].id)
            }
        }

        fun createMapFragment() {
            val mapFragment = itemView.ongoing_map_view as CustomMapView

            mapFragment.onCreate(null)
            mapFragment.onResume()
            mapFragment.getMapAsync(this)
        }

        fun setupMap() {
            if (mapReady && dataReady) {
                Log.e("E", order.toString())
                var location = (order.delivery_location!!)
                location = location.replace(" ", "")
                val loc = LatLng(location.split(",")[0].toDouble(), location.split(",")[1].toDouble())
                marker = googleMap.addMarker {
                    position(loc)
                    title(order.name)
                    icon(userIcon)
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom))
                for (transaction in transactions){
                    var location = (transaction.location!!)
                    location = location.replace(" ", "")
                    val loc = LatLng(location.split(",")[0].toDouble(), location.split(",")[1].toDouble())
                    marker = googleMap.addMarker {
                        position(loc)
                        title(transaction.name)
                        icon(storeIcon)
                    }
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom))
                }

            }
        }

        override fun onMapReady(gMap: GoogleMap) {
            googleMap = gMap
            mapReady = true
            setupMap()
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
        holder.order = curItem
        holder.dataReady = true
        holder.setupMap()
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

