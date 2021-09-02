package ru.guybydefault.powergain.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.guybydefault.powergain.R
import ru.guybydefault.powergain.calculation.BasicOneRepMaxCalculator
import ru.guybydefault.powergain.databinding.ExerciseTypeCardBinding
import ru.guybydefault.powergain.maxWeight
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.oneRepMax

class ExerciseTypeToViewHolderAdapter(private val fragment: ExerciseTypeListFragment) : RecyclerView.Adapter<ExerciseTypeViewHolder>() {

    var exerciseTypeInfo: List<ExerciseType> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseTypeViewHolder {
        val binding =
            ExerciseTypeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseTypeViewHolder(
            binding.root,
            binding.exerciseNameTextview,
            binding.maxWeightTextview,
            binding.oneRepMaxTextView,
            binding.chartBtn,
            binding.exerciseOverviewBtn,
            binding.addExerciseBtn
        )
    }

    override fun onBindViewHolder(holder: ExerciseTypeViewHolder, position: Int) {
        val exerciseTypeInfo = exerciseTypeInfo[position]
        with(holder) {
            exerciseNameTextView.text = exerciseTypeInfo.name
            maxWeightTextView.text =
                fragment.resources.getString(
                    R.string.exercise_type_list_max_weight,
                    exerciseTypeInfo.exercises.maxWeight().toString()
                )
            oneRepMaxTextView.text = fragment.resources.getString(
                R.string.exercise_type_list_one_rep_max,
                exerciseTypeInfo.exercises.oneRepMax(BasicOneRepMaxCalculator()).toInt()
                    .toString()
            )
            val typeId = exerciseTypeInfo.id
            chartBtn.setOnClickListener {
                val action = ExerciseTypeListFragmentDirections.actionTypesToCharts(typeId)
                fragment.findNavController().navigate(action)
            }
            overViewBtn.setOnClickListener {
                val action = ExerciseTypeListFragmentDirections.actionTypesToTrainings(typeId)
                fragment.findNavController().navigate(action)
            }
            addExerciseBtn.setOnClickListener {
                val action =
                    ExerciseTypeListFragmentDirections.actionTypesToCreateTraining(typeId)
                fragment.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return exerciseTypeInfo.size
    }
}