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
import com.pokumars.fitbo.data.WeatherApiService
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
        val apiService = WeatherApiService()
        GlobalScope.launch(Dispatchers.Main){
            val weatherResponse = apiService.getCurrentWeather("Vantaa").await()
            Log.d("MSG---","$weatherResponse")
            text_suggestion.text = "${weatherResponse.current.temperature}°C"
        }
        suggestionViewModel =
            ViewModelProviders.of(this).get(SuggestionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_suggestion, container, false)
        //root.text_suggestion.text =apiService.getCurrentWeather("London").toString()
        val textView: TextView = root.findViewById(R.id.text_suggestion)
        suggestionViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }


}