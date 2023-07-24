package com.bundlegh.bundledelivery.ui.home.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bundlegh.bundledelivery.R
import com.bundlegh.bundledelivery.data.db.entities.Order
import com.bundlegh.bundledelivery.data.db.entities.Transaction
import com.bundlegh.bundledelivery.data.preferences.PreferenceProvider
import com.bundlegh.bundledelivery.databinding.FragmentOrderBinding
import com.bundlegh.bundledelivery.ui.home.HomeViewModel
import com.bundlegh.bundledelivery.ui.home.HomeViewModelFactory
import com.bundlegh.bundledelivery.util.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class OrderFragment : Fragment(), KodeinAware, OnMapReadyCallback  {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private val preference: PreferenceProvider by instance()
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentOrderBinding
    private lateinit var recyclerViewStore: RecyclerView
    private lateinit var transactions: MutableList<Transaction>
    private lateinit var order: Order
    private var order_id = ""
    private lateinit var mapView: CustomMapView
    private lateinit var googleMap: GoogleMap
    private var marker: Marker? = null
    private var mapReady: Boolean = false
    private var dataReady: Boolean = false
    private val zoom = 17F
    private val userIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(activity?.applicationContext!!, R.color.primary)
        BitmapHelper.vectorToBitmap(activity?.applicationContext!!, R.drawable.home_icon, color)
    }
    private val storeIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(activity?.applicationContext!!, R.color.primary)
        BitmapHelper.vectorToBitmap(activity?.applicationContext!!, R.drawable.add_icon, color)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        recyclerViewStore = binding.recyclerViewStore
        order_id = preference.getOrderId().toString()
        setup()
        mapView = binding.fragmentOrderViewMap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        binding.buttonAccept.setOnClickListener {
            acceptOrder()
        }
        return binding.root
    }

    private fun setup(){
        Coroutines.io {
            try {
                order = viewModel.getOrder(order_id)
                val transactionResponse = viewModel.getTransactions(order.transactions!!)
                if (transactionResponse.isSuccessful!!){
                    transactions = transactionResponse.transactions
                    val transactionAdapter = TransactionAdapter(transactions)
                    activity?.runOnUiThread {
                        binding.recyclerViewStore.apply {
                            adapter = transactionAdapter
                            layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false}
                        }
                        binding.textViewAmount.text = order.amount
                        binding.textViewName.text = order.id
                        dataReady = true
                        setupMap()
                    }
                }
            } catch (e: ApiException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            } catch (e: NoInternetException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            }

        }

    }

    private fun setupMap() {
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
                Log.e("E", transaction.location.toString())
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

    private fun acceptOrder(){
        val dialog = DeliveryFeeDialog()
        dialog.show(requireFragmentManager(), "DeliveryFeeDialog")
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        mapReady = true
        setupMap()
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}


