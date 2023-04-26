package com.ceassar.test.di

import com.ceassar.test.data.datasource.model.CityEntity
import com.ceassar.test.ui.base.EmptyViewModel
import com.ceassar.test.ui.details.DetailsViewModel
import com.ceassar.test.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModel: Module = module {
    viewModel { MainViewModel(get()) }
    viewModel { EmptyViewModel() }
    viewModel { (dto: CityEntity) -> DetailsViewModel(dto = dto, get()) }
}