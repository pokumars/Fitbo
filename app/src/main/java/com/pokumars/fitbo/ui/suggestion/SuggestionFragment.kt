package com.pokumars.fitbo.ui.suggestion
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pokumars.fitbo.R
import com.pokumars.fitbo.data.GlideApp
import kotlinx.android.synthetic.main.fragment_suggestion.*
import kotlinx.android.synthetic.main.suggestion.view.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


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

    }
    private fun bindUI()=launch{
        val currentWeather = viewModel.weather.await()

        val currentLocation =viewModel.location.await()

        currentLocation.observe(this@SuggestionFragment, Observer {

        })
        currentWeather.observe(this@SuggestionFragment, Observer {
            if(it==null) return@Observer
            Log.d("feels----","${it.feelslike}")

            val rainDay = arrayOf(
                resources.getString(R.string.go_swimming),
                resources.getString(R.string.do_gym),
                resources.getString(R.string.enjoy_badminton),
                resources.getString(R.string.dance_lesson),
                resources.getString(R.string.indoor_sports))
            val snow = arrayOf(
                resources.getString(R.string.indoor_football),
                resources.getString(R.string.wall_climbing),
                resources.getString(R.string.table_tennis),
                resources.getString(R.string.dance_lesson),
                resources.getString(R.string.do_gym)
                )
            /*val summer = arrayOf(
                "You can enjoy outdoor football",
                "Go for outdoor swimming",
                "Go for running in the forest",
                "You can go to beach football")*/
            val summer = arrayOf(
                resources.getString(R.string.outdoor_football),
                resources.getString(R.string.outdoor_swim),
                resources.getString(R.string.forest_run),
                resources.getString(R.string.beach_football))
            if(it.temperature in 0..20 || it.weatherDescriptions.toString()=="rainy"){
                listCondition(rainDay)
            }
            else if(it.temperature<=0 || it.weatherDescriptions.toString()=="snowy"){
                listCondition(snow)
            }
            else{
                listCondition(summer)
            }

            updateLocation(resources.getString(R.string.vantaa))
            //updateDateToToday()
            group_loading.visibility =View.GONE
            textView_temperature.text =resources.getString(R.string.temperature, it.temperature.toString())
            textView_feels_like_temperature.text =resources.getString(R.string.feels_like, it.feelslike.toString())
            textView_wind.text =resources.getString(R.string.wind, it.windSpeed.toString(), it.windDir)
            textView_visibility.text =resources.getString(R.string.visibility, it.visibility.toString())
            textView_condition.text =it.weatherDescriptions[0]
            GlideApp.with(this@SuggestionFragment)
                .load(it.weatherIcons[0])
                .into(imageView_condition_icon)
        })
    }
    private  fun  updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title =location
    }
   /* private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = resources.getString(R.string.title_today)

    }*/

    private fun listCondition(condition:Array<String>){
        rc_view.layoutManager = LinearLayoutManager(context)
        rc_view.adapter =SuggestionAdapter(condition)
    }
}
class SuggestionAdapter (private var dataSource: Array<String>) : RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.suggestion, null)
        return ViewHolder(cellRow)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.rc_text_suggestion.text = dataSource[position]


    }

}
class ViewHolder(var view: View) : RecyclerView.ViewHolder(view)