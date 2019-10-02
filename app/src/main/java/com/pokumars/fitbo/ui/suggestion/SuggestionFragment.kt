package com.pokumars.fitbo.ui.suggestion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pokumars.fitbo.R
import com.pokumars.fitbo.ui.ForecastApplication
import kotlinx.android.synthetic.main.fragment_suggestion.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class SuggestionFragment : ScopedFragment(),KodeinAware {
    override val kodein by closestKodein()

    private val viewModelFactory:SuggestionViewModelFactory by instance()
    private lateinit var viewModel: SuggestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


      /*  suggestionViewModel =
            ViewModelProviders.of(this).get(SuggestionViewModel::class.java)
        val apiService = WeatherApiService(ConnectivityInterceptorImpl(this.context!!))
        var weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
            text_suggestion.text = it.toString()
        })
        GlobalScope.launch(Dispatchers.Main){
            *//*val weatherResponse = apiService.getCurrentWeather("Vantaa").await()
            Log.d("MSG---","$weatherResponse")
            text_suggestion.text = "${weatherResponse}°C"*//*
            weatherNetworkDataSource.fetchCurrentWeather("vantaa","en")

        }

        val root =

        val textView: TextView = root.findViewById(R.id.text_suggestion)
        suggestionViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return inflater.inflate(R.layout.fragment_suggestion, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(SuggestionViewModel::class.java)
            bindUI()
    }
    private fun bindUI()=launch{
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@SuggestionFragment, Observer {
            if(it==null) return@Observer
            text_suggestion.text=it.toString()
        })
    }


}