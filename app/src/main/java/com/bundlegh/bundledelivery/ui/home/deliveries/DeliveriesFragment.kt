package com.bundlegh.bundledelivery.ui.home.deliveries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bundlegh.bundledelivery.data.db.entities.Order
import com.bundlegh.bundledelivery.data.db.entities.Transaction
import com.bundlegh.bundledelivery.data.db.entities.Product
import com.bundlegh.bundledelivery.databinding.FragmentDeliveriesBinding
import com.bundlegh.bundledelivery.ui.home.HomeViewModel
import com.bundlegh.bundledelivery.ui.home.HomeViewModelFactory
import com.bundlegh.bundledelivery.util.*
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
        viewModel.getOngoingOrders().observe(viewLifecycleOwner) { orders ->
            orders ?: binding.root.snackbar("No ongoing orders in db")
            var tran_ids = ""
            for (order in orders)
                tran_ids += (order.transactions)
            Coroutines.io {
                try {
                    val transactionResponse = viewModel.getTransactions(tran_ids)
                    val orderOngoingAdapter = OrderOngoingAdapter(orders, transactionResponse.transactions)
                    orderOngoingAdapter.onCompletedClicked = { order_id ->
                        completedOrder(order_id)
                    }
                    activity?.runOnUiThread {
                        recyclerViewOngoing.adapter = orderOngoingAdapter
                        recyclerViewOngoing.layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false}
                    }
                } catch (e: ApiException) {
                    activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
                } catch (e: NoInternetException) {
                    activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
                }

            }

        }
        viewModel.getCompletedOrders().observe(viewLifecycleOwner) { orders ->
            orders ?: binding.root.snackbar("No new orders in db")
            val orderCompletedAdapter = OrderCompletedAdapter(orders!!)
//            orderCompletedAdapter.onOrderClicked = { order ->
//                onOrderClicked(order)
//            }
//            activity?.runOnUiThread {
            recyclerViewCompleted.adapter = orderCompletedAdapter
            recyclerViewCompleted.layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false}
//            }
        }


    }

    private fun setupRecyclerView(){
        val orderList = mutableListOf(
            Order("1", "John Doe", "1-3", "30.00", "", "", "1,2"),
            Order("2", "Kofi Flakes", "1-3", "450.00", "", "", "1,2")
        ) // From client database
//        val transaction_ids = "1,2" //concatenated order_stores
        val transactions = mutableListOf(
            Transaction("1", "Joe's Kebab", "1"), Transaction("1", "Bread Store", ""),)
        //get from api using orderStoreString
        val ongoingOrdersAdapter = OrderOngoingAdapter(orderList, transactions)
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

    private fun completedOrder(order_id: String){
        binding.root.snackbar("Completed")
        Coroutines.io {
            try {
                val completedOrderResponse = viewModel.completedOrder(order_id)
                if (completedOrderResponse.isSuccessful!!){
                    viewModel.setCompleted(order_id)
                }
            } catch (e: ApiException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            } catch (e: NoInternetException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            }
        }
    }
}