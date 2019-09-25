package com.pokumars.fitbo.ui.suggestion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SuggestionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is suggestion Fragment"
    }
    val text: LiveData<String> = _text
}