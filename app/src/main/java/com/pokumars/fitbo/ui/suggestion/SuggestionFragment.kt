package com.pokumars.fitbo.ui.suggestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pokumars.fitbo.R

class SuggestionFragment : Fragment() {

    private lateinit var suggestionViewModel: SuggestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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