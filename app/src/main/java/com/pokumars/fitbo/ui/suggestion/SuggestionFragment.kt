package com.pokumars.fitbo.ui.suggestion
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
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
import com.pokumars.fitbo.WeatherModel
import kotlinx.android.synthetic.main.fragment_suggestion.*


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
        callWebService("35","139")
        suggestionViewModel =
            ViewModelProviders.of(this).get(SuggestionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_suggestion, container, false)

        val textView: TextView = root.findViewById(R.id.text_suggestion)
        suggestionViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }


}