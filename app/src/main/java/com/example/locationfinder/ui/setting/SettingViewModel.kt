package com.example.locationfinder.ui.setting

import android.app.Application
import com.example.locationfinder.ui.base.BaseViewModel

/**
 * SettingViewModel
 */
class SettingViewModel(application: Application) :
    BaseViewModel<SettingNavigator>(application) {

    fun deleteAll() {
        mcRepository.deleteAllSavedLocation()
    }
}
