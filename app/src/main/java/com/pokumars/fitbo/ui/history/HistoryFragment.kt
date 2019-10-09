package com.pokumars.fitbo.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jjoe64.graphview.series.BarGraphSeries
import com.pokumars.fitbo.R
import com.pokumars.fitbo.util.StepsMangager
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.fragment_history.view.*
import java.util.*

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //graph.addSeries(LineGraphSeries<DataPoint>(dataPoints))
        /*graph.gridLabelRenderer.horizontalAxisTitle = "Time"
        graph.gridLabelRenderer.verticalAxisTitle = "Steps"
        graph.gridLabelRenderer.setHumanRounding(true)*/
       // graph.viewport.isXAxisBoundsManual = true
        historyViewModel =
            ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        val time = Calendar.getInstance().get(Calendar.MINUTE)
        Log.d("TIME","$time")
        val timePoints = IntArray(StepsMangager.stepsArray.size){it}
        val dataPoints = Array(StepsMangager.stepsArray.size, {DataPoint(time.toDouble(), StepsMangager.stepsArray[it].toDouble())})
        val bGraph= BarGraphSeries<DataPoint>(dataPoints)
        root.graph.addSeries(bGraph)
        root.graph.gridLabelRenderer.verticalAxisTitle ="Steps"
        root.graph.gridLabelRenderer.horizontalAxisTitle ="Time"



        historyViewModel.text.observe(this, Observer {
            textView.text = it
        })
        root.graph.addSeries(bGraph)
        return root
    }
}