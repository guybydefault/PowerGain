package ru.guybydefault.powergain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.guybydefault.powergain.databinding.ExerciseTypeCardBinding
import ru.guybydefault.powergain.databinding.FragmentExercisesTypesListBinding
import ru.guybydefault.powergain.model.ExerciseTypeInfo

class ExerciseTypeListFragment : Fragment() {

    private var binding: FragmentExercisesTypesListBinding? = null
    private lateinit var viewModel: ExercisesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ExerciseTypeToViewHolderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            (requireActivity().application as PowerGainApplication).powerGainContainer.exercisesViewModel
        recyclerViewAdapter = ExerciseTypeToViewHolderAdapter()
        viewModel.exercises.observe(this, object : Observer<List<ExerciseTypeInfo>> {
            override fun onChanged(t: List<ExerciseTypeInfo>?) {
                recyclerViewAdapter.exerciseTypeInfo = t!!
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExercisesTypesListBinding.inflate(inflater, container, false)
        binding!!.trainingTypesRecyclerView.apply {
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
        binding!!.searchExercisesEditText.afterTextChanged {
            viewModel.searchExercises(it)
        }
        binding!!.fab.setOnClickListener {
            findNavController().navigate(R.id.action_types_to_create_exercise_type)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    inner class ExerciseTypeToViewHolderAdapter : RecyclerView.Adapter<ExerciseTypeViewHolder>() {

        var exerciseTypeInfo: List<ExerciseTypeInfo> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseTypeViewHolder {
            val binding = ExerciseTypeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ExerciseTypeViewHolder(
                binding.root,
                binding.exerciseNameTextview,
                binding.chartBtn,
                binding.exerciseOverviewBtn,
                binding.addExerciseBtn
            )
        }

        override fun onBindViewHolder(holder: ExerciseTypeViewHolder, position: Int) {
            with(holder) {
                exerciseNameTextView.text = exerciseTypeInfo[position].exerciseType.name

                val typeId = exerciseTypeInfo[position].exerciseType.id
                chartBtn.setOnClickListener {
                    val action = ExerciseTypeListFragmentDirections.actionTypesToCharts(typeId)
                    findNavController().navigate(action)
                }
                overViewBtn.setOnClickListener {
                    val action = ExerciseTypeListFragmentDirections.actionTypesToTrainings(typeId)
                    findNavController().navigate(action)
                }
                addExerciseBtn.setOnClickListener {
                    val action = ExerciseTypeListFragmentDirections.actionTypesToAddExercise(typeId)
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
        val chartBtn: ImageButton,
        val overViewBtn: ImageButton,
        val addExerciseBtn: ImageButton
    ) : RecyclerView.ViewHolder(cardView) {

    }
}