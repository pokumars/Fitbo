package com.pokumars.fitbo.ui.suggestion

import androidx.lifecycle.ViewModel
import com.pokumars.fitbo.data.network.ForecastRepository
import kotlinx.coroutines.*

class SuggestionViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {
   /* private val _text = MutableLiveData<String>().apply {
        value = "This is suggestion Fragment"
    }*/
    val weather by lazyDeffred{
        forecastRepository.getCurrentWeather(true)
    }
    val location  by lazyDeffred{
        forecastRepository.getLocation()
    }

}
fun<T> lazyDeffred(block:suspend CoroutineScope.()->T):Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}
