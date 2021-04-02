package com.example.locationfinder.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.locationfinder.LocationFinder

/**
 * BaseActivity
 */
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> :
    AppCompatActivity(),
    BaseFragment.Callback {
    private lateinit var viewDataBinding: T
    private val appContext: Context = LocationFinder.appContext

    fun binding() = viewDataBinding
    fun getAppContext() = appContext

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    abstract fun getBindingVariable(): Int

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    override fun onFragmentAttached() {}

    override fun onFragmentDetached(tag: String) {}

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.setVariable(getBindingVariable(), getViewModel())
        viewDataBinding.executePendingBindings()
    }
}
