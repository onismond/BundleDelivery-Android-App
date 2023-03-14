package com.pietech.bundledelivery

import android.app.Application
import com.pietech.bundledelivery.data.db.AppDatabase
import com.pietech.bundledelivery.data.network.MyApi
import com.pietech.bundledelivery.data.network.NetworkConnectionInterceptor
import com.pietech.bundledelivery.data.preferences.PreferenceProvider
import com.pietech.bundledelivery.data.repositories.HomeRepository
import com.pietech.bundledelivery.data.repositories.UserRepository
import com.pietech.bundledelivery.ui.auth.AuthViewModelFactory
import com.pietech.bundledelivery.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class BundleApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@BundleApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { HomeRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
    }
}