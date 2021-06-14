package ru.guybydefault.powergain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.guybydefault.powergain.databinding.FragmentExerciseTrainingsBinding

import ru.guybydefault.powergain.model.ExerciseTypeInfo

class ExerciseTrainingsFragment() : Fragment() {

    private var binding: FragmentExerciseTrainingsBinding? = null
    private lateinit var viewModel: ExercisesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: TrainingViewHolderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            (requireActivity().application as PowerGainApplication).powerGainContainer.exercisesViewModel
        recyclerViewAdapter = TrainingViewHolderAdapter()
        viewModel.exercises.observe(this, object : Observer<List<ExerciseTypeInfo>> {
            override fun onChanged(t: List<ExerciseTypeInfo>?) {
//                recyclerViewAdapter.exerciseTypeInfo = t!!
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseTrainingsBinding.inflate(inflater, container, false)
//        binding!!.trainingTypesRecyclerView.apply {
//            adapter = recyclerViewAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
//        binding!!.searchExercisesEditText.afterTextChanged {
//            viewModel.searchExercises(it)
//        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding!!.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    class TrainingViewHolderAdapter : RecyclerView.Adapter<ExerciseTypeViewHolder>() {

//        var exerciseTypeInfo: List<ExerciseTypeInfo> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseTypeViewHolder {
            val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.exercise_type_card, parent, false) as CardView
//            val exerciseNameTextView = cardView.findViewById<TextView>(R.id.exercise_name_textview)
            return ExerciseTypeViewHolder(cardView)
        }

        override fun onBindViewHolder(holder: ExerciseTypeViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return 0
        }

    }

    class ExerciseTypeViewHolder(
        val cardView: CardView,
    ) : RecyclerView.ViewHolder(cardView) {

    }
}