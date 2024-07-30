package com.example.fitnesstracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WorkoutLogFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var workoutAdapter: WorkoutAdapter
    private val workouts = mutableMapOf<String, Int>() 

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("FitnessPrefs", Context.MODE_PRIVATE)

        val spinnerWorkoutType = view.findViewById<Spinner>(R.id.spinner_workout_type)
        val etWorkoutDuration = view.findViewById<EditText>(R.id.et_workout_duration)
        val btnSave = view.findViewById<Button>(R.id.btn_save)
        val btnReset = view.findViewById<Button>(R.id.btn_reset)
        val recyclerViewWorkouts = view.findViewById<RecyclerView>(R.id.recycler_view_workouts)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.workout_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerWorkoutType.adapter = adapter
        }

        workoutAdapter = WorkoutAdapter(workouts.map { Workout(it.key, it.value.toString()) }.toMutableList())
        recyclerViewWorkouts.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewWorkouts.adapter = workoutAdapter

        spinnerWorkoutType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                etWorkoutDuration.text.clear()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        btnSave.setOnClickListener {
            val workoutType = spinnerWorkoutType.selectedItem?.toString()
            val workoutDuration = etWorkoutDuration.text.toString()

            if (workoutType.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please select a workout type", Toast.LENGTH_SHORT).show()
            } else if (workoutDuration.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a workout duration", Toast.LENGTH_SHORT).show()
            } else {
                val duration = workoutDuration.toIntOrNull()
                if (duration != null) {
                    updateWorkout(workoutType, duration)
                    saveWorkouts()
                    loadWorkouts()
                } else {
                    Toast.makeText(requireContext(), "Please enter a valid number for workout duration", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnReset.setOnClickListener {
            resetWorkouts()
        }

        loadWorkouts()
    }

    private fun updateWorkout(type: String, duration: Int) {
        val currentCount = workouts.getOrDefault(type, 0)
        workouts[type] = currentCount + duration
    }

    private fun saveWorkouts() {
        val gson = Gson()
        val json = gson.toJson(workouts)
        sharedPreferences.edit().putString("Workouts", json).apply()
    }

    private fun loadWorkouts() {
        val gson = Gson()
        val json = sharedPreferences.getString("Workouts", "{}")
        val typeToken = object : TypeToken<Map<String, Int>>() {}.type
        val loadedWorkouts = gson.fromJson<Map<String, Int>>(json, typeToken)
        workouts.clear()
        workouts.putAll(loadedWorkouts)
        workoutAdapter.updateData(workouts.map { Workout(it.key, it.value.toString()) }.toMutableList())
    }

    private fun resetWorkouts() {
        workouts.clear()
        saveWorkouts()
        loadWorkouts()
    }
}
