package com.bundlegh.bundledelivery.util

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.bundlegh.bundledelivery.ui.home.HomeActivity

abstract class BaseFragment : Fragment() {
    protected open var bottomNavigationViewVisibility = View.VISIBLE

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is HomeActivity){
            var homeActivity = activity as HomeActivity
//            homeActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
        }
    }
}