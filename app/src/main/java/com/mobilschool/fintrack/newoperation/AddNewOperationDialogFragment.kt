package com.mobilschool.fintrack

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar


class AddNewOperationDialogFragment : DialogFragment() {

    //lateinit var presenter: BalanceContract.Presenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context!!)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog_add_new_operation, null)

        view?.apply {
            val categories = findViewById<Spinner>(R.id.categoriesSpinner)
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, context.resources.getStringArray(R.array.category))
            val incomeOutcomeSwitcher = findViewById<Switch>(R.id.incomeOutcomeSwitch)
            val inputDate = findViewById<EditText>(R.id.date)

            inputDate.setOnClickListener { v ->

                val datePicker = DatePickerFragment()
                datePicker.callback = { inputDate.setText(it) }
                datePicker.show(fragmentManager, "date_picker")

            }

            incomeOutcomeSwitcher.setText(R.string.outcome)
            incomeOutcomeSwitcher.setOnCheckedChangeListener { compoundButton, isChecked ->

                if (isChecked) incomeOutcomeSwitcher.setText(R.string.income)
                else incomeOutcomeSwitcher.setText(R.string.outcome)

            }
            categories.adapter = adapter
        }

        builder.setView(view)
                .setTitle(R.string.title_dialog_new_operation)
                .setPositiveButton("OK") { dialogInterface, i ->

                    activity?.apply {
                        Snackbar.make(findViewById<CoordinatorLayout>(R.id.balance_operations_layout_container), R.string.success_add_record, Snackbar.LENGTH_SHORT)
                                .show()
                    }

                }
                .setNegativeButton("Отмена") { dialogInterface, i -> }

        return builder.create()
    }

}