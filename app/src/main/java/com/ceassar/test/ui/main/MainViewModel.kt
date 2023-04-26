package com.ceassar.test.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ceassar.test.data.datasource.local.CityRepository
import com.ceassar.test.ui.base.BaseViewModel


class MainViewModel(val repository: CityRepository) : BaseViewModel() {

    val text = MutableLiveData("")

    val cityLive = Transformations.switchMap(text) { input ->
        return@switchMap repository.getSearchResultStream(input).cachedIn(viewModelScope)
            .asLiveData()
    }


}