package ru.guybydefault.powergain.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.guybydefault.powergain.databinding.TrainingCardViewBinding
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.serializer.TrainingExerciseSerializer
import java.time.format.DateTimeFormatter

class TrainingViewHolderAdapter(private val trainingListFragment: TrainingListFragment) : RecyclerView.Adapter<TrainingViewHolder>() {

    var exercises: List<TrainingExercise> = mutableListOf()
    val serializer = TrainingExerciseSerializer()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val binding =
            TrainingCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingViewHolder(
            binding.root,
            binding.trainingDate,
            binding.trainingDesc,
            binding.editBtn
        )
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val training = exercises[position]
        holder.date.text = training.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        holder.trainingDesc.text = serializer.serialize(training)
        holder.editBtn.setOnClickListener {
            val action = TrainingListFragmentDirections.actionTrainingsToCreateTraining(training.type.id, training.id)
            trainingListFragment.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

}