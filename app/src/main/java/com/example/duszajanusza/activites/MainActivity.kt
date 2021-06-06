package com.example.duszajanusza.activites

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.duszajanusza.App
import com.example.duszajanusza.activites.AddEntryActivity
import com.example.duszajanusza.adapter.EntryAdapter
import com.example.duszajanusza.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.thread

private const val REQUEST_ADD_ENTRY = 10

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val entriesAdapter by lazy { EntryAdapter((application as App).database) }

    /*
        val db = databaseBuilder(
                applicationContext, EntriesDb::class.java, "entries"
        ).build()
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        binding.currentMonth.text = "${month + 1}/$year"
        setupEntryList()
        setupAddEntryButton()
        setupGrapButton()
        monthButton()
/*
        thread {
            val entries = db.entries().getAll()
            runOnUiThread {
                binding.testList.text = entries.joinToString("\n")
            }
        }
*/

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        runOnUiThread {
            binding.monthly.text = entriesAdapter.db.entries.getCosts().toString()
        }
        super.onWindowFocusChanged(hasFocus)
    }

    private fun setupEntryList() {
        binding.cashflowList.apply {
            adapter = entriesAdapter
            layoutManager = LinearLayoutManager(context)

        }

        //val date = binding.currentMonth.text.split('/')
        entriesAdapter.load()





    }






    private fun setupAddEntryButton() = binding.addEntry.setOnClickListener {
        val addEntryIntent = Intent(this, AddEntryActivity::class.java)
        startActivityForResult(addEntryIntent, REQUEST_ADD_ENTRY)
    }

    private fun setupGrapButton() = binding.graphEntry.setOnClickListener {
        val graphEntryIntent = Intent(this, GraphActivity::class.java)
        startActivityForResult(graphEntryIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ADD_ENTRY && resultCode == Activity.RESULT_OK) {
            val date = binding.monthly.text.split('/')
            entriesAdapter.load()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun monthButton() = binding.monthlyButton.setOnClickListener{
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, y, m, _ ->
            val correctMonth = m + 1
            binding.currentMonth.text = "$correctMonth/$y"
        }, year, month, day)

        datePickerDialog.show()
    }
    }
