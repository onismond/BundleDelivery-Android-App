package com.pietech.bundledelivery.ui.home.payouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pietech.bundledelivery.databinding.FragmentPayoutsBinding
import com.pietech.bundledelivery.ui.home.HomeViewModel
import com.pietech.bundledelivery.ui.home.HomeViewModelFactory
import com.pietech.bundledelivery.util.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PayoutsFragment : BaseFragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    override var bottomNavigationViewVisibility = View.INVISIBLE
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentPayoutsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayoutsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        setupRecyclerView()
        loadPayouts()
        return binding.root
    }

    private fun setupRecyclerView() {
        viewModel.getPayouts().observe(viewLifecycleOwner) { payouts ->
            val payoutAdapter = PayoutAdapter(payouts!!)
            activity?.runOnUiThread {
                binding.recyclerViewPayouts.adapter = payoutAdapter
                binding.recyclerViewPayouts.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            }
        }
    }

    private fun loadPayouts() {
        Coroutines.io {
            try {
                val payoutResponse = viewModel.loadPayouts()
                for (payout in payoutResponse.payouts!!)
                    viewModel.savePayout(payout)
            } catch (e: ApiException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            } catch (e: NoInternetException) {
                activity?.runOnUiThread { binding.root.snackbar(e.message!!) }
            }
        }
    }
}