package com.example.locationfinder.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.locationfinder.api.McLocationService
import com.example.locationfinder.db.McDatabase
import com.example.locationfinder.db.McItemEntity
import com.example.locationfinder.models.McItemDto

/**
 * McRepository
 */
class McRepository(db: McDatabase) {

    private val dao = db.getDao()

    private val es = McExecutors()

    suspend fun getSearchResult(query: String, posX: Double, posY: Double) =
        McLocationService.mcApi.searchLocation(query = query, posX = posX, posY = posY)

    fun saveLocation(mcItemEntity: McItemEntity):Long {
        var returnValue=0L
        es.single {
            returnValue = dao.saveLocation(mcItemEntity)
        }
        Log.d("item : ", "$mcItemEntity saved")
        return returnValue
    }

    fun updateLocation(mcItemEntity: McItemEntity):Int {
        var returnValue= 0
        es.single {
            returnValue=dao.updateLocation(mcItemEntity)
        }
        Log.d("updateLocation : ", "$returnValue saved")
        return returnValue
    }

    fun deleteLocation(mcItemEntity: McItemEntity):Int {
        var returnValue=0
        es.single {
            returnValue = dao.deleteLocation(mcItemEntity)
        }
        return returnValue
    }
//
//    fun getAllSavedLocation(): LiveData<List<McItemEntity>> {
//        var data: MutableLiveData<List<McItemEntity>> = MutableLiveData()
//        es.single {
//            data.postValue(dao.getSavedLocation())
//        }
//        return data
//    }

//    fun getAllSavedLocation(): LiveData<List<McItemEntity>> {
//        return dao.getSavedLocation()
//    }

    private val myPagingConfig = Config(
        pageSize = 20,
        prefetchDistance = 40,
        enablePlaceholders = true
    )

    fun getAllPagedLocation():LiveData<PagedList<McItemEntity>>{
        return dao.getSavedLocation().toLiveData(config = myPagingConfig)
    }


    fun deleteAllSavedLocation():Int {
        var returnValue=0
        es.single {
            returnValue = dao.deleteAllSavedLocation()
        }
        return returnValue
    }
}

sealed class SearchResult<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : SearchResult<T>(data)
    class Error<T>(message: String, data: T? = null) : SearchResult<T>(data, message)
    class Loading<T> : SearchResult<T>()
}
