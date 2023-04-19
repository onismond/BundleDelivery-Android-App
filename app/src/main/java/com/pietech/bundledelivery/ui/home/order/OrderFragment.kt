package com.pietech.bundledelivery.ui.home.order

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pietech.bundledelivery.R
import com.pietech.bundledelivery.data.preferences.PreferenceProvider
import com.pietech.bundledelivery.databinding.FragmentOrderBinding
import com.pietech.bundledelivery.ui.home.HomeActivity
import com.pietech.bundledelivery.ui.home.HomeViewModel
import com.pietech.bundledelivery.ui.home.HomeViewModelFactory
import com.pietech.bundledelivery.util.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class OrderFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private val preference: PreferenceProvider by instance()
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentOrderBinding
    private lateinit var recyclerViewStore: RecyclerView
    private var order_id = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        recyclerViewStore = binding.recyclerViewStore
        order_id = preference.getOrderId().toString()
        setup()
        binding.buttonAccept.setOnClickListener {
            acceptOrder()
        }
        return binding.root
    }

    private fun setup(){
        Coroutines.io {
            try {
                val order = viewModel.getOrder(order_id)
                val transactionResponse = viewModel.getTransactions(order.transactions!!)
                println(transactionResponse)
                println(transactionResponse)
                println(order.transactions)
                if (transactionResponse.isSuccessful!!){
                    val transactionAdapter = TransactionAdapter(transactionResponse.transactions)
                    activity?.runOnUiThread {
                        binding.recyclerViewStore.apply {
                            adapter = transactionAdapter
                            layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false}
                        }
                        binding.textViewAmount.text = order.amount
                        binding.textViewName.text = order.id
                    }
                }
            } catch (e: ApiException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            } catch (e: NoInternetException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            }

        }

    }

    private fun acceptOrder(){
        val dialog = DeliveryFeeDialog()
        dialog.show(requireFragmentManager(), "DeliveryFeeDialog")
    }
//    private fun accept(){
//        Coroutines.io {
//            try {
//                val acceptOrderResponse = viewModel.acceptOrder(order_id, amount)
//                if (acceptOrderResponse.isSuccessful!!){
//                    viewModel.setOngoing(order_id)
//                    activity?.runOnUiThread {
//                        val homeActivity = activity as HomeActivity
//                        homeActivity.navigateToDeliveries()
//                    }
//                }
//
//            } catch (e: ApiException) {
//                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
//            } catch (e: NoInternetException) {
//                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
//            }
//        }
//    }

}


