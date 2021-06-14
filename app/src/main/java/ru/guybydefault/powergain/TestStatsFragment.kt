package ru.foody.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

import ru.guybydefault.powergain.R
import java.util.*


class TestStatsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.macronutrients_pie_chart, container, false)
//        val anyChartView = view.findViewById<View>(R.id.macronutrients_pie_chart) as AnyChartView
//        anyChartView.setProgressBar(view.findViewById(R.id.macronutrients_progress_bar))
//
//        val data: MutableList<DataEntry> = ArrayList()
//        data.add(ValueDataEntry("Белки", 120))
//        data.add(ValueDataEntry("Жиры", 70))
//        data.add(ValueDataEntry("Углеводы", 210))
//
//        val pie = AnyChart.pie()
//        pie.credits().enabled(false)
//        pie.data(data)
//        pie.title("Статистика по макронутриентам")
//        anyChartView.setChart(pie)

        setupMacroNutrientsPieChart(view)
        setupMicroNutrientsChart(view)

        return view
    }

    private fun setupMicroNutrientsChart(view: View) {
        val chartView = view.findViewById<View>(R.id.micronutrients_bar_chart) as HorizontalBarChart

    }

    private fun setupMacroNutrientsPieChart(view: View) {
        val chartView = view.findViewById<View>(R.id.macronutrients_pie_chart) as PieChart

        val pieEntries = mutableListOf<PieEntry>()
        pieEntries.add(PieEntry(10f, "Green"))
        pieEntries.add(PieEntry(30f, "Yellow"))
        pieEntries.add(PieEntry(40f, "Белки"))

        val pieEntries2 = mutableListOf<PieEntry>()
        pieEntries2.add(PieEntry(20f, "test"))

        val pieDataSet = PieDataSet(pieEntries, "Диаграмма макронутриентов")
//        pieDataSet.colors(1, 1)
        val data = PieData(pieDataSet)
//        data.addDataSet(PieDataSet(pieEntries2, "test"))
//        data.addEntry(PieEntry(20f, "test"), 1)
        chartView.data = data
        chartView.invalidate()
    }

}