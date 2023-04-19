package com.pietech.bundledelivery.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pietech.bundledelivery.data.db.entities.Order
import com.pietech.bundledelivery.data.preferences.PreferenceProvider
import com.pietech.bundledelivery.databinding.FragmentHomeBinding
import com.pietech.bundledelivery.ui.home.HomeViewModel
import com.pietech.bundledelivery.ui.home.HomeViewModelFactory
import com.pietech.bundledelivery.util.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : BaseFragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private val preference: PreferenceProvider by instance()
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerViewNew: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        recyclerViewNew = binding.recyclerViewNew
        setProfileDetails()
        getNewOrders()
        setupReyclerView()
        binding.constraintAccount.setOnClickListener {
            onAccountClicked()
        }
        return binding.root
    }

    private fun setupReyclerView(){
        viewModel.getAvailableOrders().observe(viewLifecycleOwner) { orders ->
            orders ?: binding.root.snackbar("No new orders in db")
            val newOrdersAdapter = OrderNewAdapter(orders!!)
            newOrdersAdapter.onOrderClicked = { order ->
                onOrderClicked(order)
            }
            activity?.runOnUiThread {
                recyclerViewNew.adapter = newOrdersAdapter
                recyclerViewNew.layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false}
            }
        }
    }

    private fun getNewOrders(){
        Coroutines.io {
            try{
                val orderResponse = viewModel.getNewOrders()
                println(orderResponse)
                println(orderResponse)
                if (orderResponse.isSuccessful!!) {
                    for (order in orderResponse.orders)
                        viewModel.saveOrder(order)
                }
            } catch (e: ApiException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            } catch (e: NoInternetException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            }

        }
    }
    private fun setProfileDetails() {
        viewModel.getProfile().observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                binding.textViewNotClearedCount.text = profile.not_cleared_count.toString()
                binding.textViewNotClearedAmount.text = profile.not_cleared_total
                binding.textViewNotClearedBalance.text = profile.not_cleared_balance
                binding.textViewAccountBalance.text = profile.account_balance
            }

        }
        Coroutines.io {
            try {
                val profileResponse = viewModel.loadProfile()
                if (profileResponse.isSuccessful!!) {
                    print(profileResponse)
                    viewModel.saveProfile(profileResponse.profile)
                }
            } catch (e: ApiException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            } catch (e: NoInternetException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            }
        }
    }

    private fun onOrderClicked(order: Order){
        preference.saveOrderId(order.id)
        val action = HomeFragmentDirections.actionHomeFragmentToOrderFragment()
        this.findNavController().navigate(action)
    }

    private fun onAccountClicked() {
        val action = HomeFragmentDirections.actionHomeFragmentToPayoutsFragment()
        this.findNavController().navigate(action)
    }

}


