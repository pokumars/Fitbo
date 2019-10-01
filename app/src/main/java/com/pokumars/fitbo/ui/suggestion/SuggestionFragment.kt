package com.pokumars.fitbo.ui.suggestion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pokumars.fitbo.R
import com.pokumars.fitbo.data.ConnectivityInterceptorImpl
import com.pokumars.fitbo.data.WeatherApiService
import com.pokumars.fitbo.data.WeatherNetworkDataSourceImpl
import kotlinx.android.synthetic.main.fragment_suggestion.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class SuggestionFragment : Fragment() {

    private lateinit var suggestionViewModel: SuggestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        suggestionViewModel =
            ViewModelProviders.of(this).get(SuggestionViewModel::class.java)
        val apiService = WeatherApiService(ConnectivityInterceptorImpl(this.context!!))
        var weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
            text_suggestion.text = it.toString()
        })
        GlobalScope.launch(Dispatchers.Main){
            /*val weatherResponse = apiService.getCurrentWeather("Vantaa").await()
            Log.d("MSG---","$weatherResponse")
            text_suggestion.text = "${weatherResponse}°C"*/
            weatherNetworkDataSource.fetchCurrentWeather("vantaa","en")

        }

        val root = inflater.inflate(R.layout.fragment_suggestion, container, false)

        val textView: TextView = root.findViewById(R.id.text_suggestion)
        suggestionViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }


}