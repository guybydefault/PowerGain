package ru.guybydefault.powergain.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import ru.guybydefault.powergain.PowerGainApplication
import ru.guybydefault.powergain.container
import ru.guybydefault.powergain.databinding.FragmentChartBinding
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.onChartValueSelectedListener
import ru.guybydefault.powergain.serializer.TrainingExerciseSerializer
import ru.guybydefault.powergain.viewmodel.ChartViewModel


class ChartFragment() : Fragment() {

    private lateinit var binding: FragmentChartBinding
    private val args: ChartFragmentArgs by navArgs()
    private lateinit var viewModel: ChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = container().chartViewModel
        binding = FragmentChartBinding.inflate(inflater, container, false)
        val exercises =
            (requireActivity().application as PowerGainApplication).container.dataRepository.getTrainingsByType(
                args.exerciseTypeId
            )


        val arr = arrayOf("Интенсивность", "Тоннаж", "Макс. вес", "1ПМ (расчетный)")
        binding.chartSelectionSpinner.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arr
        )

        binding.chartSelectionSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(requireContext(), "Hey ${arr[position]}", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        viewModel.highlightedTraining.observe(viewLifecycleOwner, { training ->
            binding.trainingInfoTextView.text =
                training.date.toString() + " " + serializer.serialize(training)
        })
        setupLineChart(binding.lineChart, exercises)

        return binding.root
    }

    val serializer = TrainingExerciseSerializer()

    private fun setupLineChart(chart: LineChart, trainings: List<TrainingExercise>) {
        val lineData = generateLineData(trainings)
        chart.data = lineData

        chart.onChartValueSelectedListener({ e: Entry?, h: Highlight? ->
            viewModel.highlightedTraining.postValue(e!!.data as TrainingExercise)
        })

        chart.invalidate()
    }


    private fun generateLineData(trainings: List<TrainingExercise>): LineData? {
        val data = LineData()
        val maxWeightEntries: ArrayList<Entry> = ArrayList()
        val intensityEntries: ArrayList<Entry> = ArrayList()
        for ((index, training) in trainings.withIndex()) {
            maxWeightEntries.add(
                Entry(
                    index + 0.5f,
                    training.maxWeight.toFloat()
                )
            )
            intensityEntries.add(Entry(index + 0.5f, training.intensity.toFloat(), training))
        }
        val intensitySet = LineDataSet(intensityEntries, "Intensity (volume / reps)")
        //        val maxWeightSet = LineDataSet(maxWeightEntries, "Max Weight (kg)")
//        maxWeightSet.color = Color.rgb(240, 238, 70)
//        maxWeightSet.lineWidth = 2.5f
//        maxWeightSet.setCircleColor(Color.rgb(240, 238, 70))
//        maxWeightSet.circleRadius = 5f
//        maxWeightSet.fillColor = Color.rgb(240, 238, 70)
//        maxWeightSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//        maxWeightSet.setDrawValues(true)
//        maxWeightSet.valueTextSize = 10f
//        maxWeightSet.valueTextColor = Color.rgb(240, 238, 70)
//        maxWeightSet.axisDependency = YAxis.AxisDependency.LEFT
//        data.addDataSet(maxWeightSet)
        data.addDataSet(intensitySet)
        return data
    }

}