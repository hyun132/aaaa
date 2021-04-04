package com.example.locationfinder.ui.location

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.locationfinder.db.McItemEntity
import com.example.locationfinder.ui.base.BaseViewModel

/**
 * LocationViewModel
 */
class LocationViewModel(application: Application) :
    BaseViewModel<LocationNavigator>(application) {
    private val _savedResult: MutableLiveData<PagedList<McItemEntity>> = MutableLiveData()
    val savedList:LiveData<PagedList<McItemEntity>>
        get() = _savedResult
    private var _handleFavoritButtonClicked = MutableLiveData<LocationFragment.Event<Boolean>>()
    var isdeleted = MutableLiveData<Boolean>(true)

    init {
        mcRepository.getAllPagedLocation().observeForever { list ->
            _savedResult.postValue(list)
        }
        isdeleted.observeForever {
            if (isdeleted.value == false) {
                getSavedList()
                isdeleted.postValue(true)
            }
        }
    }

    fun deleteMcItemEntity(mcItemEntity: McItemEntity) {
        Log.d(
            "log : ",
            "deleteMcItemEntity $mcItemEntity"
        )
        mcRepository.deleteLocation(mcItemEntity)
    }

    private fun updateMcItemEntity(mcItemEntity: McItemEntity) {
        Log.d("clicked : ", "updateMcItemEntity : ${mcItemEntity.placeName} , ${mcItemEntity.favorite}")
        mcRepository.updateLocation(mcItemEntity)
    }

    val savedEntityList = mcRepository.getAllPagedLocation().observeForever { savedList ->
        _savedResult.postValue(savedList)
    }

    fun getSavedList(){
        _savedResult.postValue(mcRepository.getAllPagedLocation().value)
    }

    val pagedList =mcRepository.getAllPagedLocation()

    fun handleClickFavoritButtonEvent(item: McItemEntity) {
        item.favorite=!item.favorite
        isdeleted.postValue(false)
        Log.d("clicked : ", "handleClickFavoritButtonEvent : ${item.placeName} , ${item.favorite}")
        updateMcItemEntity(item)
    }

    fun onClickButton() {
        _handleFavoritButtonClicked.value = LocationFragment.Event(true)
    }
}