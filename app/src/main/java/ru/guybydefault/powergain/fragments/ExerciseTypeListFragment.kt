package ru.guybydefault.powergain.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.guybydefault.powergain.*
import ru.guybydefault.powergain.databinding.FragmentExercisesTypesListBinding
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.viewmodel.ExercisesViewModel

class ExerciseTypeListFragment : Fragment() {

    private lateinit var binding: FragmentExercisesTypesListBinding
    private lateinit var viewModel: ExercisesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ExerciseTypeToViewHolderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            (requireActivity().application as PowerGainApplication).container.exercisesViewModel
        recyclerViewAdapter = ExerciseTypeToViewHolderAdapter(this)
        viewModel.exercises.observe(this, object : Observer<List<ExerciseType>> {
            override fun onChanged(t: List<ExerciseType>?) {
                recyclerViewAdapter.exerciseTypeInfo = t!!
                recyclerViewAdapter.notifyDataSetChanged() // TODO diffUtil
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesTypesListBinding.inflate(inflater, container, false)
        binding.trainingTypesRecyclerView.apply {
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.searchExercisesEditText.afterTextChanged {
            viewModel.searchExercises(it)
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_types_to_create_exercise_type)
        }
        return binding.root
    }

}