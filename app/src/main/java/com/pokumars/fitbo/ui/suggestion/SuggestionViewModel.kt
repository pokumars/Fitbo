package com.pokumars.fitbo.ui.suggestion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pokumars.fitbo.data.network.ForcastRepository
import kotlinx.coroutines.*

class SuggestionViewModel(private val forecastRepository: ForcastRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is suggestion Fragment"
    }
    val weather by lazyDeffred{
        forecastRepository.getCurrentWeather(true)
    }
    val text: LiveData<String> = _text

}
fun<T> lazyDeffred(block:suspend CoroutineScope.()->T):Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}