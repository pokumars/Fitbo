package com.pokumars.fitbo.ui.endexercise


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pokumars.fitbo.R
import kotlinx.android.synthetic.main.fragment_end_exercise.*

/**
 * A simple [Fragment] subclass.
 */
class EndExerciseFragment : Fragment() {
    var calories= ""
    var distanceTravelled= ""
    var stepsRun= ""
    var timeExercised = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setValuesFromArgs()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        distanceResultTV.text = resources.getString(R.string.km, distanceTravelled)
        caloriesResultTV.text = resources.getString(R.string.calories, calories)
        stepsResultTV.text= resources.getString(R.string.steps, stepsRun)
        timeResultTV.text = timeExercised
    }

private fun setValuesFromArgs(){
    val args = EndExerciseFragmentArgs.fromBundle(arguments!!)
    calories = args.numKCal
    distanceTravelled= args.numKilometres
    stepsRun = args.numSteps
    timeExercised = args.timeExercisedArg
}
}
