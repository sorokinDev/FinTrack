package com.mobilschool.fintrack.ui.newoperation

import android.app.Dialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.mobilschool.fincalc.TypeOperation
import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.Operation
import com.mobilschool.fintrack.ui.DatePickerFragment


class AddNewOperationDialogFragment : DialogFragment(), AddNewOperationContract.View {

    override lateinit var presenter: AddNewOperationContract.Presenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context!!)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog_add_new_operation, null)

        view?.apply {
            val categories = findViewById<Spinner>(R.id.categoriesSpinner)
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, context.resources.getStringArray(R.array.category))
            val incomeOutcomeSwitcher = findViewById<Switch>(R.id.incomeOutcomeSwitch)
            val inputDate = findViewById<EditText>(R.id.date)
            val amount = findViewById<EditText>(R.id.amount)

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

            builder.setView(view)
                    .setTitle(R.string.title_dialog_new_operation)
                    .setPositiveButton("OK") { dialogInterface, i ->
                        activity?.apply {
                             val operation = Operation(amount.text.toString(), FinTrackerApplication.BASE_CURRENCY, inputDate.text.toString(), categories?.selectedItem as String, if (incomeOutcomeSwitcher.isChecked) TypeOperation.INCOME else TypeOperation.OUTCOME)
                             presenter.addOperation(operation)
                        }

                    }
                    .setNegativeButton("Отмена") { dialogInterface, i -> }

        }

        return builder.create()
    }


    override fun showMessage(isSuccess: Boolean) {
        val caption = if (isSuccess) R.string.success_add_record else R.string.failed_add_record
        Toast.makeText(context,caption, Toast.LENGTH_SHORT).show()
    }

}