package ru.guybydefault.powergain.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.guybydefault.powergain.container
import ru.guybydefault.powergain.databinding.FragmentCreateTrainingBinding
import ru.guybydefault.powergain.viewmodel.CreateTrainingViewModel

class CreateAndEditTrainingFragment : Fragment() {

    private lateinit var viewModel: CreateTrainingViewModel
    private val adapter = TrainingSetViewHolderAdapter()
    val args: CreateAndEditTrainingFragmentArgs by navArgs()

    private lateinit var binding: FragmentCreateTrainingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = container().createTrainingViewModel
        if (args.trainingId != -1) {
            viewModel.setupViewModelForTraining(args.trainingId)
        } else {
            viewModel.setupViewModelForExerciseType(args.exerciseTypeId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTrainingBinding.inflate(inflater, container, false)
        binding.setsRecyclerView.apply {
            adapter = this@CreateAndEditTrainingFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.exerciseType.observe(viewLifecycleOwner, {
            binding.exerciseTypeName.text = it.name
        })
        viewModel.trainingSets.observe(viewLifecycleOwner, { trainingSets ->
            adapter.updateTrainingSetList(trainingSets)
//            if (trainingSets.isEmpty()) {
//                binding.noSetsTextView.visibility = View.VISIBLE
//                binding.setsRecyclerView.visibility = View.INVISIBLE
//            } else {
//                binding.noSetsTextView.visibility = View.INVISIBLE
//                binding.setsRecyclerView.visibility = View.VISIBLE
//            }
        })
    }

}