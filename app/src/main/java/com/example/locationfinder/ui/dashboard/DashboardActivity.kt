package com.example.locationfinder.ui.dashboard

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.locationfinder.BR
import com.example.locationfinder.R
import com.example.locationfinder.databinding.ActivityDashboardBinding
import com.example.locationfinder.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * DashboardActivity
 */
class DashboardActivity :
    BaseActivity<ActivityDashboardBinding, DashboardViewModel>(),
    DashboardNavigator {

    private val vm: DashboardViewModel by viewModel()
    override fun getLayoutId() = R.layout.activity_dashboard
    override fun getBindingVariable() = BR.viewModel
    override fun getViewModel(): DashboardViewModel {
        vm.setNavigator(this)
        return vm
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding().homeBottomNav, navController)

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}