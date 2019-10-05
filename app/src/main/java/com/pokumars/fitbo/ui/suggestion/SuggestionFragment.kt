package com.pokumars.fitbo.ui.suggestion

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pokumars.fitbo.R
import com.pokumars.fitbo.data.GlideApp
import com.pokumars.fitbo.data.network.LocationProvider
import com.pokumars.fitbo.data.network.LocationProviderImpl
import com.pokumars.fitbo.ui.ForecastApplication
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_suggestion.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class SuggestionFragment : ScopedFragment(),KodeinAware{
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
        readJSONFromAsset()
        Log.d("JSON","${readJSONFromAsset()}")
    }
    private fun bindUI()=launch{
        val currentWeather = viewModel.weather.await()

        val currentLocation =viewModel.location.await()

        currentLocation.observe(this@SuggestionFragment, Observer {
            /*if (location==null)return@Observer
            updateLocation(location.name)*/
            //updateLocation(it.name)

        })
        currentWeather.observe(this@SuggestionFragment, Observer {
            if(it==null) return@Observer

           updateLocation("Vantaa")
            updateDateToToday()
            group_loading.visibility =View.GONE
            textView_temperature.text ="${it.temperature}°C"
            textView_feels_like_temperature.text ="It feels like ${it.feelslike} °C"
            textView_wind.text ="Wind: ${it.windSpeed} m/s ${it.windDir}"
            textView_visibility.text ="Visibility: ${it.visibility} km"
            textView_condition.text =it.weatherDescriptions[0]
            Log.d("icons...","${it.weatherIcons}")
            GlideApp.with(this@SuggestionFragment)
                .load("${it.weatherIcons[0]}")
                .into(imageView_condition_icon)
        })
    }
    private  fun  updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title =location
    }
    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"

    }
    fun readJSONFromAsset():JSONObject? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val iStream = activity?.assets?.open("exercise.json")
            val size = iStream?.available()
            if (size!=null){
                val buffer = ByteArray(size)
                iStream.read(buffer)
                iStream.close()
                json = String(buffer, charset)
            }

        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return JSONObject(json)
    }


}