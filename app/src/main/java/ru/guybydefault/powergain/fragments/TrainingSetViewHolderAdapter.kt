package ru.guybydefault.powergain.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.guybydefault.powergain.calculation.BasicOneRepMaxCalculator
import ru.guybydefault.powergain.databinding.TrainingSetCardBinding
import ru.guybydefault.powergain.model.TrainingSet

class TrainingSetViewHolderAdapter(
) :
    RecyclerView.Adapter<TrainingSetViewHolder>() {
    private var trainingSetList: List<TrainingSet> = mutableListOf()

    fun updateTrainingSetList(trainingSetList: List<TrainingSet>) {
        this.trainingSetList = trainingSetList
        notifyDataSetChanged() // TODO diffUtil
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingSetViewHolder {
        val binding =
            TrainingSetCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingSetViewHolder(binding)
    }

    val repMaxCalculator = BasicOneRepMaxCalculator()
    override fun onBindViewHolder(holder: TrainingSetViewHolder, position: Int) {
        val set = trainingSetList[position]
        // TODO correct rep max calc
        holder.binding.oneRepMaxVal.text = set.oneRepMax(repMaxCalculator).toInt().toString()
        holder.binding.repsVal.setText(set.repetitions.toString())
        holder.binding.weightVal.setText(set.weight.toString())
        holder.binding.setId.text = position.toString()
        holder.binding.editBtn.setOnClickListener {
            holder.binding.weightVal.isEnabled = true
            holder.binding.repsVal.isEnabled = true
        }
    }

    override fun getItemCount(): Int {
        return trainingSetList.size
    }
}
