package ru.guybydefault.powergain.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import ru.guybydefault.powergain.PowerGainApplication
import ru.guybydefault.powergain.viewmodel.CreateTrainingViewModel
import ru.guybydefault.powergain.R
import ru.guybydefault.powergain.container
import ru.guybydefault.powergain.databinding.FragmentCreateTrainingBinding

class CreateTrainingFragment : Fragment() {

    private lateinit var viewModel: CreateTrainingViewModel
    val args: CreateTrainingFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = container().createTrainingViewModel
        viewModel.exerciseTypeId = args.exerciseTypeId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCreateTrainingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        val view = binding.root
        return view
    }

    // TODO set weight based on the previous training
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = container().createTrainingViewModel
    }

}