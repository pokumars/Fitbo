package com.pokumars.fitbo.ui.suggestion
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pokumars.fitbo.R
import com.pokumars.fitbo.WeatherModel
import com.pokumars.fitbo.data.WeatherApiService
import com.pokumars.fitbo.data.WeatherResponse
import kotlinx.android.synthetic.main.fragment_suggestion.*
import kotlinx.android.synthetic.main.fragment_suggestion.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SuggestionFragment : Fragment() {

    private lateinit var suggestionViewModel: SuggestionViewModel
    private fun callWebService(lat:String,lon:String){
        val call = WeatherModel.service.dailyTemp(lat,lon,"e66a2daf65be84a5ab6ae92eca5dd8ec")
        val value =object : Callback<WeatherModel.Model.Result>{
            override fun onResponse(call:Call<WeatherModel.Model.Result>,response:
            Response<WeatherModel.Model.Result>?){
                if (response != null){
                    val res:WeatherModel.Model.Result= response.body()!!
                    Log.d("msg","${res.result.temperature}")
                    text_suggestion.text = res.result.temperature.toString()
                }
            }

            override fun onFailure(call: Call<WeatherModel.Model.Result>, t: Throwable) {
                Log.e("DBG",t.toString())
            }
        }
        call.enqueue(value)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val apiService = WeatherApiService()
        GlobalScope.launch(Dispatchers.Main){
            val weatherResponse = apiService.getCurrentWeather("london").await()
            text_suggestion.text = weatherResponse.toString()
        }
        suggestionViewModel =
            ViewModelProviders.of(this).get(SuggestionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_suggestion, container, false)
    root.text_suggestion.text =apiService.getCurrentWeather("London").toString()
        val textView: TextView = root.findViewById(R.id.text_suggestion)
        suggestionViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }


}