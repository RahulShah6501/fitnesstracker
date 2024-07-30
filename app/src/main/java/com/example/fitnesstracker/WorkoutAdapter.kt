package com.example.fitnesstracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutAdapter(private var workouts: MutableList<Workout>) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.bind(workout)
    }

    override fun getItemCount(): Int = workouts.size

    fun updateData(newData: List<Workout>) {
        workouts.clear()
        workouts.addAll(newData)
        notifyDataSetChanged()
    }

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val workoutType: TextView = itemView.findViewById(R.id.workout_type)
        private val workoutDuration: TextView = itemView.findViewById(R.id.workout_duration)

        fun bind(workout: Workout) {
            workoutType.text = workout.type
            workoutDuration.text = workout.duration
        }
    }
}
