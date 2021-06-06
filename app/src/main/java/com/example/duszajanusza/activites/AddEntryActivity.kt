package com.example.duszajanusza.activites

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.duszajanusza.App
import com.example.duszajanusza.databinding.ActivityAddEntryBinding
import com.example.duszajanusza.model.dto.EntryDto
import java.util.*
import kotlin.concurrent.thread

class AddEntryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddEntryBinding.inflate(layoutInflater) }
    private val db by lazy { (application as App).database }
    override fun onCreate(savedInstanceState: Bundle?) {
        var bundle :Bundle ?=intent.extras
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        intent?.getIntExtra("id", 0)?.let { setupSaveButton(it) }

        binding.money.setText(intent?.getDoubleExtra("money",0.0)?.toString())
        binding.location.setText(intent?.getStringExtra("shop"))
        binding.date.setText(intent?.getStringExtra("date"))
        binding.category.setText(intent?.getStringExtra("category"))
        setupDatePick()
        setupShareButton()

    }
private fun setupDatePick() = binding.date.setOnClickListener {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, y, m, d ->
            val correctMonth = m + 1
            binding.date.setText("$d/$correctMonth/$y")
        }, year, month, day)

        datePickerDialog.show()
    }
    private fun setupSaveButton(intExtra: Int?) = binding.saveButton.setOnClickListener {
        val money = binding.money.text.toString().toDouble()
        val location = binding.location.text.toString()
        val date = binding.date.text.toString()
        val category = binding.category.text.toString()

                    thread {
                        if(intExtra != null){
                            db.entries.updateEntry( EntryDto(
                                id = intExtra,
                                money = money,
                                shop = location,
                                date = date,
                                category = category,

                                ))
                        }
                            db.entries.addEntry(
                                EntryDto(
                                    money = money,
                                    shop = location,
                                    date = date,
                                    category = category,

                                    ))



                        setResult(Activity.RESULT_OK)
                        finish()

                    }

        }
    private fun setupShareButton() = binding.shareButton.setOnClickListener {
        val money = binding.money.text.toString().toDouble()
        val location = binding.location.text.toString()
        val date = binding.date.text.toString()
        val category = binding.category.text.toString()

        val intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "Place: $location\nValue: $money\nDate: $date\nCategory: $category")
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent,"Share using:"))
    }
    }
