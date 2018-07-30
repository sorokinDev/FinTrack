package com.mobilschool.fintrack.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    lateinit var callback: (String) -> Unit

    lateinit var formattedDate:String
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {

        val c = Calendar.getInstance()
        c.set(year, month, day)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        formattedDate = sdf.format(c.time)
        callback(formattedDate)
    }

}