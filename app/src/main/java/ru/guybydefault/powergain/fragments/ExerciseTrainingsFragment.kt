package ru.guybydefault.powergain.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.guybydefault.powergain.PowerGainApplication
import ru.guybydefault.powergain.R
import ru.guybydefault.powergain.viewmodel.TrainingsViewModel
import ru.guybydefault.powergain.databinding.FragmentExerciseTrainingsBinding
import ru.guybydefault.powergain.databinding.TrainingCardViewBinding

import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.serializer.TrainingExerciseSerializer

class ExerciseTrainingsFragment() : Fragment() {

    private lateinit var binding: FragmentExerciseTrainingsBinding
    private lateinit var viewModel: TrainingsViewModel
    private lateinit var adapter: TrainingViewHolderAdapter

    private val args: ExerciseTrainingsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TrainingViewHolderAdapter()
        viewModel =
            (requireActivity().application as PowerGainApplication).container.trainingsViewModel
        viewModel.exerciseTypeId = args.exerciseTypeId
        viewModel.exercises.observe(this) { trainings ->
            adapter.exercises = trainings
            updateTrainingsCount(trainings.size)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseTrainingsBinding.inflate(inflater, container, false)
        binding.trainingsRecyclerView.apply {
            setHasFixedSize(true)
            adapter = this@ExerciseTrainingsFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.fab.setOnClickListener {
            val action =
                ExerciseTrainingsFragmentDirections.actionTrainingsToCreateTraining(args.exerciseTypeId)
            findNavController().navigate(action)
        }
        binding.chartBtn.setOnClickListener {
            val action =
                ExerciseTrainingsFragmentDirections.actionTrainingsToChart(args.exerciseTypeId)
            findNavController().navigate(action)
        }
        return binding.root
    }

    private fun updateTrainingsCount(size: Int) {
        binding.exerciseCount.text =
            "${resources.getString(R.string.fragment_trainings_count)} ${size}"
    }

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
            holder.date.text = training.date.toString()
            holder.trainingDesc.text = serializer.serialize(training)
        }

        override fun getItemCount(): Int {
            return exercises.size
        }

    }

    class ExerciseTypeViewHolder(
        val cardView: CardView,
        val date: TextView,
        val trainingDesc: TextView
    ) : RecyclerView.ViewHolder(cardView) {

    }
}