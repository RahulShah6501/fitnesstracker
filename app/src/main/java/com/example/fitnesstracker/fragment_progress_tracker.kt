package com.example.fitnesstracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProgressTrackerFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_tracker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("FitnessPrefs", Context.MODE_PRIVATE)

        val tvProgressSummary = view.findViewById<TextView>(R.id.tv_progress_summary)
        displayProgress(tvProgressSummary)
    }

    private fun displayProgress(textView: TextView) {
        val workoutType = sharedPreferences.getString("WorkoutType", "No data")
        val workoutDuration = sharedPreferences.getString("WorkoutDuration", "No data")
        textView.text = "Type: $workoutType\nDuration: $workoutDuration"
    }
}
