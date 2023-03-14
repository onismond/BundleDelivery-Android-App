package com.pietech.bundledelivery.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.pietech.bundledelivery.R
import com.pietech.bundledelivery.databinding.ActivitySplashScreenBinding
import com.pietech.bundledelivery.ui.auth.AuthViewModel
import com.pietech.bundledelivery.ui.auth.AuthViewModelFactory
import com.pietech.bundledelivery.ui.auth.LoginActivity
import com.pietech.bundledelivery.ui.home.HomeActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SplashScreenActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getLoggedInUser().observe(this) { user ->
                if (user != null) {
                    intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 1000)
    }
}