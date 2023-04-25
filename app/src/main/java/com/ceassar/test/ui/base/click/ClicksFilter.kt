package com.ceassar.test.ui.base.click

import android.os.SystemClock

class ClicksFilter {
    private var lastTimeClicked: Long = 0

    fun filterClick(durationMillis: Long = 400): Boolean {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < durationMillis) {
            return false
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        return true
    }
}

fun <T> ClicksFilter.debounce(durationMillis: Long, t: T, action: (T) -> Unit) {
    if (filterClick(durationMillis)) action(t)
}

fun ClicksFilter.debounce(durationMillis: Long, action: () -> Unit) {
    if (filterClick(durationMillis)) action()
}