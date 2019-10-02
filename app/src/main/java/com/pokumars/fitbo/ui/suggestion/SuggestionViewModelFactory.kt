package com.pokumars.fitbo.ui.suggestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pokumars.fitbo.data.network.ForcastRepository

class SuggestionViewModelFactory(private val forecastRepository: ForcastRepository):ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SuggestionViewModel(forecastRepository) as T
    }
}