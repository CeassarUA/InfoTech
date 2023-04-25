package com.ceassar.test.ui.base

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    val loadingStateLiveData = MediatorLiveData<Boolean>()

    fun showLoading(show: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            loadingStateLiveData.value = show
        }
    }

    fun showError(
        exception: Exception? = null,
        message: String? = null,
        @StringRes messageRes: Int? = null,
    ) {
       //show Error
    }


    protected fun launchAsync(
        showLoading: Boolean = true,
        showError: Boolean = true,
        onError: ((Exception) -> Unit)? = null,
        finally: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            if (showLoading) showLoading(true)
            try {
                block.invoke(this)
            } catch (e: CancellationException) {

            } catch (e: Exception) {
                Log.e("Error", "while launching async", e)
                if (showError) {
                    if (onError == null) showError(e)
                    else onError.invoke(e)
                }
            } finally {
                if (showLoading) showLoading(false)
                finally?.invoke()
            }
        }
    }

    protected suspend fun launch(
        showLoading: Boolean = true,
        showError: Boolean = true,
        onError: ((Exception) -> Unit)? = null,
        finally: (() -> Unit)? = null,
        block: suspend () -> Unit,
    ) {
        if (showLoading) showLoading(true)
        try {
            block.invoke()
        } catch (e: CancellationException) {

        } catch (e: Exception) {
            Log.e("Error", "while launching async", e)
            if (showError) {
                if (onError == null) showError(e)
                else onError.invoke(e)
            }
        } finally {
            if (showLoading) showLoading(false)
            finally?.invoke()
        }
    }

}

data class ErrorResult(
    val exception: Exception? = null,
    val message: String? = null,
    @StringRes val messageRes: Int? = null,
)
