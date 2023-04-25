package com.ceassar.test.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ceassar.test.ui.base.click.ClicksFilter
import com.ceassar.test.ui.base.click.SafeClickListener
import com.ceassar.test.ui.base.click.debounce
import com.ceassar.test.ui.base.interfaces.BaseViewInterface


abstract class BaseFragment<VBinding : ViewDataBinding>(
    private val bindFactory: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
) : Fragment(), SafeClickListener {

    private val clicksFilter = ClicksFilter()

    override fun View.onClick(durationMillis: Long, action: (view: View) -> Unit) {
        setOnClickListener {
            clicksFilter.debounce(durationMillis, it, action)
        }
    }

    private var viewDataBinding: VBinding? = null

    protected val binding: VBinding
        get() = viewDataBinding
            ?: throw IllegalStateException("Cannot access view in after view destroyed and before view creation")

    open val viewModel: ViewModel = EmptyViewModel()
    private val baseViewModel
        get() = viewModel as? BaseViewModel

    protected abstract fun initialization(view: View, savedInstanceState: Bundle?)

    protected open fun VBinding.dataBind() {

    }

    protected fun LiveData<Boolean>.listenLoadingState(progressView: View? = null) {
        observe(viewLifecycleOwner) { isLoading ->
            progressView?.let {
                it.isVisible = isLoading
            } ?: let {
                if (isLoading) showProgressDialog()
                else hideProgressDialog()
            }
        }
    }

    override fun onStop() {
        hideProgressDialog()
        super.onStop()
    }


    fun showProgressDialog() {
        (activity as? BaseViewInterface)?.showProgressDialog()
    }

    fun hideProgressDialog() {
        activity?.runOnUiThread {
            (activity as? BaseViewInterface)?.hideProgressDialog()
        }
    }

    protected inline fun safeBind(block: VBinding.() -> Unit = {}): VBinding? {
        val tag = this::class.simpleName ?: "BaseFragment"
        return try {
            binding
        } catch (e: Exception) {
            Log.w(tag, "failed to get binding", e)
            null
        }?.also {
            try {
                block(it)
            } catch (e: Exception) {
                Log.w(tag, "failed to use binding", e)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return bind(inflater, container)
    }
    private fun bind(inflater: LayoutInflater, container: ViewGroup?): View? {
        viewDataBinding = bindFactory(inflater, container, false).also {
            it.setLifecycleOwner {
                lifecycle
            }
            it.dataBind()
        }
        return viewDataBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization(view, savedInstanceState)
        observeViewModel()
    }


    private fun observeViewModel() {
        baseViewModel?.apply {
//            exceptionLive.observe(viewLifecycleOwner) {
//                it.getContentIfNotHandled()?.showStableErrors()
//            }

        }
    }

}

