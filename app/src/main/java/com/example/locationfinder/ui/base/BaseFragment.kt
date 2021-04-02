package com.example.locationfinder.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.locationfinder.BR
import com.example.locationfinder.LocationFinder

/**
 * BaseFragment
 */
abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> :
    Fragment() {
    private lateinit var viewDataBinding: T
    private var activity: BaseActivity<*, *>? = null
    private val appContext: Context = LocationFinder.appContext

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun getViewModel(): V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            activity = context
            activity?.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewDataBinding.setVariable(getBindingVariable(), getViewModel())
        viewDataBinding.setVariable(BR.viewModel, getViewModel())
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    fun binding() = viewDataBinding

    fun getBaseActivity() = activity

    fun getAppContext() = appContext

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}
