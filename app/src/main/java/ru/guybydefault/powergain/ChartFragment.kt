package ru.guybydefault.powergain

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import ru.guybydefault.powergain.databinding.FragmentChartBinding
import ru.guybydefault.powergain.model.TrainingExercise
import kotlin.random.Random


class ChartFragment : Fragment() {

    private lateinit var binding: FragmentChartBinding
    val args: ChartFragmentArgs by navArgs<ChartFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        val exercises =
            (requireActivity().application as PowerGainApplication).powerGainContainer.dataRepository.getTrainingsByType(args.exerciseTypeId)
        setupCombinedChart(
            binding.combinedChart,
            exercises
        )
        setupLineChart(binding.lineChart, exercises)
        return binding.root
    }

    private fun setupCombinedChart(chart: CombinedChart, exercises: List<TrainingExercise>) {
        val combinedData = CombinedData()
        combinedData.setData(generateLineData(exercises))
        combinedData.setData(generateBarData())
        chart.data = combinedData
        chart.invalidate()
    }

    private fun setupLineChart(chart: LineChart, trainings: List<TrainingExercise>) {
        val lineData = generateLineData(trainings)
        chart.data = lineData
        chart.invalidate()
    }

    val count = 12
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
            intensityEntries.add(Entry(index + 0.5f, training.intensity.toFloat()))
        }
        val maxWeightSet = LineDataSet(maxWeightEntries, "Max Weight (kg)")
        val intensitySet = LineDataSet(intensityEntries, "Intensity (volume / reps)")
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


    private fun generateBarData(): BarData? {
        val entries1: ArrayList<BarEntry> = ArrayList()
        val entries2: ArrayList<BarEntry> = ArrayList()
        for (index in 0 until count) {
            entries1.add(BarEntry(0f, Random(0).nextInt(0, 25).toFloat()))

            // stacked
            entries2.add(
                BarEntry(
                    0f,
                    floatArrayOf(
                        Random(0).nextInt(12, 15).toFloat(),
                        Random(0).nextInt(12, 15).toFloat()
                    )
                )
            )
        }
        val set1 = BarDataSet(entries1, "Bar 1")
        set1.color = Color.rgb(60, 220, 78)
        set1.valueTextColor = Color.rgb(60, 220, 78)
        set1.valueTextSize = 10f
        set1.axisDependency = YAxis.AxisDependency.LEFT
        val set2 = BarDataSet(entries2, "")
        set2.stackLabels = arrayOf("Stack 1", "Stack 2")
        set2.setColors(Color.rgb(61, 165, 255), Color.rgb(23, 197, 255))
        set2.valueTextColor = Color.rgb(61, 165, 255)
        set2.valueTextSize = 10f
        set2.axisDependency = YAxis.AxisDependency.LEFT
        val groupSpace = 0.06f
        val barSpace = 0.02f // x2 dataset
        val barWidth = 0.45f // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
        val d = BarData(set1, set2)
        d.barWidth = barWidth

        // make this BarData object grouped
        d.groupBars(0f, groupSpace, barSpace) // start at x = 0
        return d
    }
}