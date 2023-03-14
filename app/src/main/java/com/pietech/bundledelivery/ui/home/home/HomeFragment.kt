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
import com.pietech.bundledelivery.util.BaseFragment
import com.pietech.bundledelivery.util.OrderNewAdapter
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
        setupReyclerView()
        return binding.root
    }

    private fun setupReyclerView(){
        val newOrdersAdapter = OrderNewAdapter(mutableListOf(
                Order("1", "John Doe", "1-3", "30.00"),
                Order("2", "Kofi Flakes", "1-3", "450.00"
            )))
        newOrdersAdapter.onOrderClicked = { order ->
            onOrderClicked(order)
        }
        activity?.runOnUiThread {
            recyclerViewNew.adapter = newOrdersAdapter
            recyclerViewNew.layoutManager = object: LinearLayoutManager(requireActivity().applicationContext) { override fun canScrollVertically() = false}
        }

    }

    private fun onOrderClicked(order: Order){
        preference.saveOrderId(order.id)
        val action = HomeFragmentDirections.actionHomeFragmentToOrderFragment()
        this.findNavController().navigate(action)
    }
}