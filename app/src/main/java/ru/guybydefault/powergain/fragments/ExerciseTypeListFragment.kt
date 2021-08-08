package ru.guybydefault.powergain.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.guybydefault.powergain.*
import ru.guybydefault.powergain.calculation.BasicOneRepMaxCalculator
import ru.guybydefault.powergain.databinding.ExerciseTypeCardBinding
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
        recyclerViewAdapter = ExerciseTypeToViewHolderAdapter()
        viewModel.exercises.observe(this, object : Observer<List<ExerciseType>> {
            override fun onChanged(t: List<ExerciseType>?) {
                recyclerViewAdapter.exerciseTypeInfo = t!!
                recyclerViewAdapter.notifyDataSetChanged()
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

    inner class ExerciseTypeToViewHolderAdapter : RecyclerView.Adapter<ExerciseTypeViewHolder>() {

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
                    resources.getString(
                        R.string.exercise_type_list_max_weight,
                        exerciseTypeInfo.exercises.maxWeight().toString()
                    )
                oneRepMaxTextView.text = resources.getString(
                    R.string.exercise_type_list_one_rep_max,
                    exerciseTypeInfo.exercises.oneRepMax(BasicOneRepMaxCalculator()).toInt()
                        .toString()
                )
                val typeId = exerciseTypeInfo.id
                chartBtn.setOnClickListener {
                    val action = ExerciseTypeListFragmentDirections.actionTypesToCharts(typeId)
                    findNavController().navigate(action)
                }
                overViewBtn.setOnClickListener {
                    val action = ExerciseTypeListFragmentDirections.actionTypesToTrainings(typeId)
                    findNavController().navigate(action)
                }
                addExerciseBtn.setOnClickListener {
                    val action =
                        ExerciseTypeListFragmentDirections.actionTypesToCreateTraining(typeId)
                    findNavController().navigate(action)
                }
            }
        }

        override fun getItemCount(): Int {
            return exerciseTypeInfo.size
        }
    }

    class ExerciseTypeViewHolder(
        val cardView: CardView,
        val exerciseNameTextView: TextView,
        val maxWeightTextView: TextView,
        val oneRepMaxTextView: TextView,
        val chartBtn: ImageButton,
        val overViewBtn: ImageButton,
        val addExerciseBtn: ImageButton
    ) : RecyclerView.ViewHolder(cardView)
}