package com.pokumars.fitbo.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jjoe64.graphview.series.BarGraphSeries
import com.pokumars.fitbo.R
import com.pokumars.fitbo.util.StepsMangager
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.view.*
import java.util.*

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        historyViewModel =
            ViewModelProviders.of(this).get(HistoryViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        Log.d("TIME","$time")
        val dataPoints = Array(StepsMangager.stepsArray.size, {DataPoint(time.toDouble(), StepsMangager.stepsArray[it].toDouble())})
        val bGraph= LineGraphSeries<DataPoint>(dataPoints)
        root.graph.addSeries(bGraph)
        root.graph.gridLabelRenderer.verticalAxisTitle ="Steps"
        root.graph.gridLabelRenderer.horizontalAxisTitle ="Time(24hr)"
        root.graph.scrollX
        root.graph.addSeries(bGraph)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        distanceHistoryTextView.text= resources.getString(R.string.distance_covered, String.format("%.2f", historyViewModel.distanceTravelled))
        caloriesHistoryTextView.text = resources.getString(R.string.calories, String.format("%.1f", historyViewModel.calories))
        stepsHistoryTextView.text =resources.getString(R.string.steps, String.format("%.0f", historyViewModel.universalStepCount()))
    }
}