package ru.guybydefault.powergain.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.guybydefault.powergain.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DateSelectionFragment : DialogFragment() {

//    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = DateSel.inflate(inflater, container, false)
//        return binding.root
        return inflater.inflate(R.layout.exercise_type_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_types_to_trainings)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}