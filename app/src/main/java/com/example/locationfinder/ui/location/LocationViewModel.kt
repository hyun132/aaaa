package com.example.locationfinder.ui.location

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.locationfinder.db.McItemEntity
import com.example.locationfinder.ui.base.BaseViewModel

/**
 * LocationViewModel
 */
class LocationViewModel(application: Application) :
    BaseViewModel<LocationNavigator>(application) {
//    private var _savedResult: MediatorLiveData<List<McItemEntity>> = MediatorLiveData()
    private var _handleFavoritButtonClicked = MutableLiveData<LocationFragment.Event<Boolean>>()

    fun deleteMcItemEntity(mcItemEntity: McItemEntity) {
        Log.d(
            "log : ",
            "deleteMcItemEntity $mcItemEntity"
        )
        mcRepository.deleteLocation(mcItemEntity)
    }

    fun updateMcItemEntity(mcItemEntity: McItemEntity) {
        mcRepository.updateLocation(mcItemEntity)
    }

    val savedEntityList = mcRepository.getAllSavedLocation()

    fun handleClickFavoritButtonEvent(item : McItemEntity){
        updateMcItemEntity(item)
    }
    fun onClickButton(){
        _handleFavoritButtonClicked.value=LocationFragment.Event(true)
    }
}