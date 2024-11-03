package com.example.calendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.takvim.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var selectedDateText: TextView
    private lateinit var eventList: ListView
    private val events = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectedDateText = findViewById(R.id.selectedDate)
        eventList = findViewById(R.id.eventList)
        val buttonPickDateTime: Button = findViewById(R.id.buttonPickDateTime)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, events)
        eventList.adapter = adapter

        buttonPickDateTime.setOnClickListener {
            pickDateTime()
        }
    }

    private fun pickDateTime() {
        val calendar = Calendar.getInstance()

        // Tarih Seçimi
        val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)

            // Saat Seçimi
            val timePicker = TimePickerDialog(this, { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                // Tarih ve saati biçimlendirme
                val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val formattedDateTime = dateTimeFormat.format(calendar.time)

                // Seçilen tarih ve saati göster ve etkinliğe ekle
                selectedDateText.text = "Selected Date and Time: $formattedDateTime"
                addEvent(formattedDateTime)

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

            timePicker.show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    private fun addEvent(dateTime: String) {
        events.add("Event on $dateTime")
        adapter.notifyDataSetChanged()
    }
}
