package com.example.locationfinder.ui.base

import com.example.locationfinder.ui.dashboard.DashboardViewModel
import com.example.locationfinder.ui.location.LocationViewModel
import com.example.locationfinder.ui.map.MapViewModel
import com.example.locationfinder.ui.map.search.SearchViewModel
import com.example.locationfinder.ui.setting.SettingViewModel
import com.example.locationfinder.ui.setting.license.LicenseViewModel
import com.example.locationfinder.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModelModules
 */
val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { MapViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { LocationViewModel(get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { LicenseViewModel(get()) }
}

val appModules = listOf(viewModelModule)
