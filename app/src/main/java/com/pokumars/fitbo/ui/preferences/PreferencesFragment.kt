package com.pokumars.fitbo.ui.preferences


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

import com.pokumars.fitbo.R
import kotlinx.android.synthetic.main.fragment_preferences.*

/**
 * A simple [Fragment] subclass.
 */
class PreferencesFragment : Fragment() {
    private lateinit var preferencesViewModel: PreferencesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        preferencesViewModel =
            ViewModelProviders.of(this).get(PreferencesViewModel::class.java)


        return inflater.inflate(R.layout.fragment_preferences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        updateWeightBtn.setOnClickListener { updateWeight() }
        updateStepsTargetBtn.setOnClickListener { updateStepsTarget() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title="Preference"
    }
    private fun updateWeight(){
        val userWeight =  userWeightET.text.toString()
        if(userWeight.trim().length > 1&& Integer.parseInt(userWeight)> 20 && Integer.parseInt(userWeight)< 200 ){

            preferencesViewModel.setWeight(userWeight.toFloat())
            //distanceCoveredTextView.setText(resources.getString(R.string.today_page_km, String.format("%.2f",todayViewModel.distanceTravelled)))
            Toast.makeText(context, resources.getString(R.string.weight_set, String.format("%.1f",preferencesViewModel.getWeight())), Toast.LENGTH_LONG).show()
            userWeightET.text.clear()

        }else{
            Toast.makeText(context, resources.getString(R.string.set_correct_weight), Toast.LENGTH_LONG).show()
            userWeightET.text.clear()
        }
    }

    private fun updateStepsTarget(){
        val stepTarget = stepTargetET.text.toString()

        if(stepTarget.trim().length > 2&& Integer.parseInt(stepTarget)> 100){

            preferencesViewModel.setDailyStepTarget(stepTarget.toFloat())
            //distanceCoveredTextView.setText(resources.getString(R.string.today_page_km, String.format("%.2f",todayViewModel.distanceTravelled)))
            Toast.makeText(context, resources.getString(R.string.daily_target_set, String.format("%.0f",preferencesViewModel.getDailyStepTarget())), Toast.LENGTH_LONG).show()
            stepTargetET.text.clear()

        }else{
            Toast.makeText(context, resources.getString(R.string.set_correct_target), Toast.LENGTH_LONG).show()
            stepTargetET.text.clear()
        }

    }


}
