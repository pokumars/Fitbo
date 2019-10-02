package com.pokumars.fitbo.ui.suggestion

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pokumars.fitbo.R
import com.pokumars.fitbo.ui.ForecastApplication
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_suggestion.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.time.temporal.Temporal


class SuggestionFragment : ScopedFragment(),KodeinAware {
    override val kodein by closestKodein()

    private val viewModelFactory:SuggestionViewModelFactory by instance()
    private lateinit var viewModel: SuggestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
            //text_suggestion.text=it.toString()
            group_loading.visibility =View.GONE
            updateLocation("vantaa")
            textView_temperature.text ="${it.temperature}°C"
            textView_feels_like_temperature.text ="It feels like ${it.feelslike} °C"
            textView_wind.text ="Wind: ${it.windSpeed} m/s ${it.windDir}"
            textView_visibility.text ="Visibility: ${it.visibility} km"
        })
    }
    private  fun  updateLocation(location: String){
        (activity as AppCompatActivity)?.supportActionBar?.title =location
    }

}