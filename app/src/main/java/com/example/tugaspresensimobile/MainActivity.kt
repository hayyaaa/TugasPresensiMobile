package com.example.tugaspresensimobile

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import com.example.tugaspresensimobile.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            var selectedDate = ""
            var selectedTime = ""
            var selectedAttendance = ""

            calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
                selectedDate = dateFormat.format(calendar.time)
            }

            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                val AM_PM = if (hourOfDay < 12) {
                    "AM"
                } else {
                    "PM"
                }
                val minute = if (minute < 10) {
                    "0" + minute.toString()
                } else {
                    minute
                }
                selectedTime = "$hourOfDay:$minute $AM_PM"
            }

            val attendance = resources.getStringArray(R.array.attendance)
            val adapterKeterangan = ArrayAdapter<String>(this@MainActivity, R.layout.spinner, attendance)
                spinnerAttendance.adapter = adapterKeterangan

            spinnerAttendance.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        if (attendance[position] == "Hadir Tepat Waktu") {
                            editTxtKeterangan.visibility = View.GONE
                        } else {
                            editTxtKeterangan.visibility = View.VISIBLE
                        }
                        selectedAttendance = attendance[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        TODO("Not yet implemented")
                    }
                }

            btnSubmit.setOnClickListener {
                val keterangan = editTxtKeterangan.text.toString()
                if (selectedDate != "" && selectedTime != "") {
                    if (selectedAttendance == "Hadir Tepat Waktu") {
                        Toast.makeText(this@MainActivity, "Presensi berhasil $selectedDate jam $selectedTime", Toast.LENGTH_LONG).show()
                    } else {
                        if (keterangan != "") {
                            Toast.makeText(this@MainActivity, "$selectedAttendance dengan keterangan $keterangan.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "Keterangan tidak boleh kosong.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Harap melengkapi waktu presensi!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
