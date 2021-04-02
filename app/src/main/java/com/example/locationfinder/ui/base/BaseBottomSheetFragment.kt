package com.example.locationfinder.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.locationfinder.LocationFinder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * BaseBottomSheetFragment
 */
abstract class BaseBottomSheetFragment<T : ViewDataBinding, V : BaseViewModel<*>> :
    BottomSheetDialogFragment() {
    protected lateinit var viewDataBinding: T
        private set

    private var activity: BaseActivity<*, *>? = null
    private val appContext: Context = LocationFinder.appContext

    fun getBaseActivity() = activity

    fun getAppContext() = appContext

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
        viewDataBinding.setVariable(getBindingVariable(), getViewModel())
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    /**
     * onDetach
     */
    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}
