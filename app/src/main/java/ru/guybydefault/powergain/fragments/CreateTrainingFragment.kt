package ru.guybydefault.powergain.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.guybydefault.powergain.container
import ru.guybydefault.powergain.databinding.FragmentCreateTrainingBinding
import ru.guybydefault.powergain.databinding.TrainingSetCardBinding
import ru.guybydefault.powergain.model.TrainingSet
import ru.guybydefault.powergain.viewmodel.CreateTrainingViewModel

class CreateTrainingFragment : Fragment() {

    private lateinit var viewModel: CreateTrainingViewModel
    val args: CreateTrainingFragmentArgs by navArgs()

    private lateinit var binding: FragmentCreateTrainingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = container().createTrainingViewModel
        viewModel.setupViewModelForExerciseType(args.exerciseTypeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTrainingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        val view = binding.root
        viewModel.trainingSets.observe(viewLifecycleOwner, { trainingSets ->
            displaySets(trainingSets)
        })
        return view
    }

    private fun displaySets(trainingSets: List<TrainingSet>) {
        binding.setsList.removeAllViews()
        for (set in trainingSets) {
            val setBinding = TrainingSetCardBinding.inflate(
                LayoutInflater.from(requireContext()),
                binding.setsList,
                true
            )
            binding.setsList.invalidate()
            setBinding.repsVal.text = set.repetitions.toString()
            setBinding.weightVal.text = set.weight.toString()
        }
    }

}