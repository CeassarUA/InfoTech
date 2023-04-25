package com.ceassar.test.ui.base.click

import android.view.View

interface SafeClickListener {
    fun View.onClick(durationMillis: Long = 400, action: (view: View) -> Unit)
}