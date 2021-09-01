package ru.guybydefault.powergain.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.guybydefault.powergain.databinding.TrainingCardViewBinding
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.serializer.TrainingExerciseSerializer
import java.time.format.DateTimeFormatter

class TrainingViewHolderAdapter : RecyclerView.Adapter<ExerciseTypeViewHolder>() {

    var exercises: List<TrainingExercise> = mutableListOf()
    val serializer = TrainingExerciseSerializer()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseTypeViewHolder {
        val binding =
            TrainingCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseTypeViewHolder(
            binding.root,
            binding.trainingDate,
            binding.trainingDesc
        )
    }

    override fun onBindViewHolder(holder: ExerciseTypeViewHolder, position: Int) {
        val training = exercises[position]
        holder.date.text = training.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        holder.trainingDesc.text = serializer.serialize(training)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

}