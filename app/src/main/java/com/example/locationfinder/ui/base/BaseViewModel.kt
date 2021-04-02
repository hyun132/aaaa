package com.example.locationfinder.ui.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.example.locationfinder.db.McDatabase
import com.example.locationfinder.repository.McRepository
import java.lang.ref.WeakReference

/**
 * BaseViewModel
 */
abstract class BaseViewModel<N>(application: Application) :
    AndroidViewModel(application) {
    private var navigator: WeakReference<N>? = null
    private val isLoading = ObservableField(false)

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    fun getIsLoading() = isLoading

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator() = navigator?.get()

    val db = McDatabase.getDatabase()
    val mcRepository = McRepository(db)
}
