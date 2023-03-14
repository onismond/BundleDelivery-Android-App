package com.pietech.bundledelivery.ui.home.deliveries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pietech.bundledelivery.data.db.entities.Order
import com.pietech.bundledelivery.data.db.entities.OrderStore
import com.pietech.bundledelivery.data.db.entities.Product
import com.pietech.bundledelivery.data.db.entities.Store
import com.pietech.bundledelivery.databinding.FragmentDeliveriesBinding
import com.pietech.bundledelivery.ui.home.HomeViewModel
import com.pietech.bundledelivery.ui.home.HomeViewModelFactory
import com.pietech.bundledelivery.util.BaseFragment
import com.pietech.bundledelivery.util.OrderCompletedAdapter
import com.pietech.bundledelivery.util.OrderNewAdapter
import com.pietech.bundledelivery.util.OrderOngoingAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DeliveriesFragment : BaseFragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentDeliveriesBinding
    private lateinit var recyclerViewOngoing: RecyclerView
    private lateinit var recyclerViewCompleted: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveriesBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        recyclerViewOngoing = binding.recyclerViewOngoing
        recyclerViewCompleted = binding.recyclerViewCompleted
        setupRecyclerViews()
        return binding.root
    }

    private fun setupRecyclerViews(){
        val orderList = mutableListOf(
            Order("1", "John Doe", "1-3", "30.00", "", "", "1,2"),
            Order("2", "Kofi Flakes", "1-3", "450.00", "", "", "1,2")
        ) // From client database
        val orderStoresString = "1,2" //concatenated order_stores
        val orderStores = mutableListOf(
            OrderStore("1", "Joe's Kebab", mutableListOf(
                Product("1", "Kebab chicken", "5.00", "", "", ""),
                Product("2", "Kebab pork", "5.00", "", "", "")
        )), OrderStore("1", "Bread Store", mutableListOf(
                Product("1", "Kebab chicken", "5.00", "", "", ""),
                Product("2", "Kebab pork", "5.00", "", "", "")
        )),) //get from api using orderStoreString
        val ongoingOrdersAdapter = OrderOngoingAdapter(orderList, orderStores)
        activity?.runOnUiThread {
            recyclerViewOngoing.adapter = ongoingOrdersAdapter
            recyclerViewOngoing.layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false}
        }

        val completedOrdersAdapter = OrderCompletedAdapter(mutableListOf(
            Order("1", "John Doe", "1-3", "30.00", "completed", "05/03/2023 1:00"),
            Order("2", "Kofi Flakes", "1-3", "450.00", "completed", "06/03/2023 12:15")
        ))
        activity?.runOnUiThread {
            recyclerViewCompleted.adapter = completedOrdersAdapter
            recyclerViewCompleted.layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false}
        }
    }
}