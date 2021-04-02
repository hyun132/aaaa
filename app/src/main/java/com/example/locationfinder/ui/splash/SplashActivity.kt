package com.example.locationfinder.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.locationfinder.BR
import com.example.locationfinder.R
import com.example.locationfinder.constant.McConstants.SPLASH_DELAY
import com.example.locationfinder.databinding.ActivitySplashBinding
import com.example.locationfinder.ui.base.BaseActivity
import com.example.locationfinder.ui.dashboard.DashboardActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * SplashActivity
 */
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(),
    SplashNavigator {

    private val splashViewModel: SplashViewModel by viewModel()
    override fun getLayoutId() = R.layout.activity_splash
    override fun getBindingVariable() = BR.viewModel
    override fun getViewModel(): SplashViewModel {
        splashViewModel.setNavigator(this)
        return splashViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.myLooper()).postDelayed({
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }, SPLASH_DELAY)
    }
}