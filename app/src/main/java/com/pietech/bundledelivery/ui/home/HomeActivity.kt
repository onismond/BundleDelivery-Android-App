package com.pietech.bundledelivery.ui.home

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.pietech.bundledelivery.R
import com.pietech.bundledelivery.data.db.entities.Order
import com.pietech.bundledelivery.data.network.OrderListener
import com.pietech.bundledelivery.data.network.WebSocketManager
import com.pietech.bundledelivery.data.preferences.PreferenceProvider
import com.pietech.bundledelivery.databinding.ActivityHomeBinding
import com.pietech.bundledelivery.util.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_accept_order.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import kotlin.concurrent.thread

class HomeActivity : AppCompatActivity(), KodeinAware, OrderListener, DeliveryFeeDialog.FeeDialogListener {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private val preference: PreferenceProvider by instance()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: HomeViewModel
    private val serverUrl = "ws://192.168.241.83:80/api/order1"
//    private val serverUrl = "wss://websocket-echo.glitch.me"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        navController = findNavController(R.id.nav_fragment)
        val appBarConfiguration = AppBarConfiguration
            .Builder(R.id.homeFragment, R.id.deliveriesFragment, R.id.profileFragment)
            .build()
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        WebSocketManager.init(serverUrl, this)
        thread {
            kotlin.run {
                WebSocketManager.connect()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_nav, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_refresh -> {
            true
        }
        R.id.action_logout -> {
            binding.root.snackbar("log out")
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_fragment).navigateUp() || super.onNavigateUp()

    fun setBottomNavigationVisibility(visibility: Int){
        binding.bottomNavigationView.visibility = visibility
    }

    fun navigateToProfile(){
        val item = bottomNavigationView.menu.findItem(R.id.profileFragment)
        NavigationUI.onNavDestinationSelected(item, navController)
    }

    override fun onConnectSuccess() {
        binding.root.snackbar("Websocket connected")
    }

    override fun onConnectFailed() {
        binding.root.snackbar("Websocket connect failed")
    }

    override fun onClose() {
        binding.root.snackbar("Websocket closed")
    }

    override fun onOrder(order: Order?) {
        println(order)
        binding.root.snackbar(order.toString())
    }

    override fun onDialogConfirmClick(amount: String, dialog: Dialog) {
        Coroutines.io {
            try {
                val order_id = preference.getOrderId().toString()
                val acceptOrderResponse = viewModel.acceptOrder(order_id, amount)
                if (acceptOrderResponse.isSuccessful!!){
                    viewModel.setOngoing(order_id)
                    runOnUiThread{ navigateToDeliveries(dialog) }

                }
            } catch (e: ApiException) {
                binding.root.snackbar(e.message!!)
            } catch (e: NoInternetException) {
                binding.root.snackbar(e.message!!)
            }
        }
    }


    fun navigateToDeliveries(){
        val item = bottomNavigationView.menu.findItem(R.id.deliveriesFragment)
        NavigationUI.onNavDestinationSelected(item, navController)

    }

    fun navigateToDeliveries(dialog: Dialog){
        val item = bottomNavigationView.menu.findItem(R.id.deliveriesFragment)
        NavigationUI.onNavDestinationSelected(item, navController)
        dialog.cancel()
    }


}