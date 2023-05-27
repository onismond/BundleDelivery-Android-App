package com.bundlegh.bundledelivery.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bundlegh.bundledelivery.databinding.ActivityLoginBinding
import com.bundlegh.bundledelivery.ui.home.HomeActivity
import com.bundlegh.bundledelivery.util.ApiException
import com.bundlegh.bundledelivery.util.Coroutines
import com.bundlegh.bundledelivery.util.NoInternetException
import com.bundlegh.bundledelivery.util.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.getLoggedInUser().observe(this) { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }

        binding.textViewForgotPassword.setOnClickListener {
            Intent(this, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        binding.buttonLogin.setOnClickListener {
            loginUser()
        }

    }

    private fun loginUser() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        // todo validate the inputs

        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            binding.root.snackbar("Invalid email or password")
            return
        }

        // for fragments use viewLifeCycle.lifeCycleScope

        Coroutines.io {
            try {
                val authResponse = viewModel.userLogin(email, password)
                if (authResponse.user != null && !authResponse.token.isNullOrEmpty()) {
                    val user = authResponse.user
                    user.token = authResponse.token
                    viewModel.saveLoggedInUser(user)
                } else {
                    binding.root.snackbar(authResponse.message!!)
                }
            } catch (e: ApiException) {
                binding.root.snackbar(e.message!!)
            } catch (e: NoInternetException) {
                binding.root.snackbar(e.message!!)
            }
        }
    }
}