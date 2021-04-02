package com.example.locationfinder.ui.map.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.locationfinder.db.McItemEntity
import com.example.locationfinder.models.McKakaoSearchResponseDto
import com.example.locationfinder.repository.SearchResult
import com.example.locationfinder.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * SearchViewModel
 */
class SearchViewModel(application: Application) : BaseViewModel<SearchNavigator>(application) {
    private val _searchResults: MutableLiveData<SearchResult<McKakaoSearchResponseDto>> =
        MutableLiveData()
    val searchSearchResult: LiveData<SearchResult<McKakaoSearchResponseDto>>
        get() = _searchResults

    fun getSearchItem(query: String, posX: Double, posY: Double) =
        viewModelScope.launch(Dispatchers.IO) {
            _searchResults.postValue(
                handleSearchResult(
                    mcRepository.getSearchResult(
                        query = query,
                        posX = posX,
                        posY = posY
                    )
                )
            )
        }

    private fun handleSearchResult(response: Response<McKakaoSearchResponseDto>): SearchResult<McKakaoSearchResponseDto> {
        if (response.isSuccessful) {
            response.body()?.let {
                return SearchResult.Success(it)
            }
        }
        Log.d("error: ", response.message())
        return SearchResult.Error(response.message())
    }

    fun saveItem(mcItemEntity: McItemEntity) {
        mcRepository.saveLocation(mcItemEntity)
    }
}