package com.example.duszajanusza.activites


import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.duszajanusza.App
import com.example.duszajanusza.Utils
import com.example.duszajanusza.adapter.DataPoint
import com.example.duszajanusza.adapter.GraphView
import com.example.duszajanusza.databinding.ActivityGraphBinding
import com.example.duszajanusza.model.Entries
import java.time.YearMonth
import java.util.*
import kotlin.concurrent.thread


class GraphActivity: AppCompatActivity(){
    private val binding by lazy { ActivityGraphBinding.inflate(layoutInflater) }
    private val db by lazy { (application as App).database }
    private var entries = emptyList<Entries>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        binding.summarySelectedMonth.text = "${month + 1}/$year"
        regenerateChart()
        setupDatePicker()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupViewChartButton()
        }
    }

    private fun setupDatePicker() = binding.summarySelectMonthButton.setOnClickListener {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, y, m, _ ->
            val correctMonth = m + 1
            binding.summarySelectedMonth.text = "$correctMonth/$y"
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun regenerateChart() = thread {
        val newEntries = db.entries.getAll().map { it.toModel() }
        val date = binding.summarySelectedMonth.text.split('/')
        entries = Utils().getEntriesForMonth(newEntries, date[0].toInt(), date[1].toInt())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        regenerateChart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupViewChartButton() = binding.buttonViewChart.setOnClickListener {
        val date = binding.summarySelectedMonth.text.split('/')
        val yearMonthObject: YearMonth = YearMonth.of(date[1].toInt(), date[0].toInt())
        val daysInMonth: Int = yearMonthObject.lengthOfMonth()

        val dataPoints = ArrayList<DataPoint>()
        val sortedEntries = Utils().getEntriesSortedByDay(entries)
        var value = 0.0f

        dataPoints.add(DataPoint(0.0f, 0.0f))
        for (day in 1 until daysInMonth + 1) {
            for (entry in sortedEntries) {
                if (entry.date.split('/')[0].toInt() == day) {
                    value += entry.money.toFloat();

                }
            }
            dataPoints.add(DataPoint(day.toFloat(), value))
        }

        val graphView = GraphView(this, null)
        graphView.setData(dataPoints)
        setContentView(graphView)
        Log.d("ENTRIES: ", entries.toString())
        Log.d("DATAPOINTS: ", dataPoints.toString())
    }
}