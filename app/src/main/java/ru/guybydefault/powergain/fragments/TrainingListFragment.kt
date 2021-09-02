package ru.guybydefault.powergain.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.guybydefault.powergain.R
import ru.guybydefault.powergain.container
import ru.guybydefault.powergain.databinding.FragmentTrainingListBinding
import ru.guybydefault.powergain.viewmodel.ExercisesViewModel

class TrainingListFragment() : Fragment() {

    private lateinit var binding: FragmentTrainingListBinding
    private lateinit var viewModel: ExercisesViewModel
    private lateinit var adapter: TrainingViewHolderAdapter

    private val args: TrainingListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TrainingViewHolderAdapter(this)
        viewModel = container().exercisesViewModel
        viewModel.setupViewModel(args.exerciseTypeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingListBinding.inflate(inflater, container, false)
        binding.trainingsRecyclerView.apply {
            setHasFixedSize(true)
            adapter = this@TrainingListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.fab.setOnClickListener {
            val action =
                TrainingListFragmentDirections.actionTrainingsToCreateTraining(args.exerciseTypeId)
            findNavController().navigate(action)
        }
        binding.chartBtn.setOnClickListener {
            val action =
                TrainingListFragmentDirections.actionTrainingsToChart(args.exerciseTypeId)
            findNavController().navigate(action)
        }
        viewModel.trainingExercises.observe(viewLifecycleOwner) { trainings ->
            adapter.exercises = trainings
            setExerciseTypeName(viewModel.exerciseType.name)
            updateTrainingsCount(trainings.size)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    private fun updateTrainingsCount(size: Int) {
        binding.exerciseCount.text =
            "${resources.getString(R.string.fragment_trainings_count)} ${size}"
    }

    private fun setExerciseTypeName(name: String) {
        binding.exerciseTypeName.text = name
    }

}