package com.mobilschool.fintrack.ui.balance

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobilschool.fincalc.Operations
import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.base.BaseFragment
import com.mobilschool.fintrack.util.Utils
import com.razerdp.widget.animatedpieview.AnimatedPieView
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig
import com.razerdp.widget.animatedpieview.data.SimplePieInfo
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class BalanceFragment : BaseFragment(), BalanceView {


    @Inject
    @InjectPresenter
    lateinit var presenter: BalancePresenter

    @ProvidePresenter
    fun providePresenter() = presenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_current_balance, container, false)
        presenter.attachView(this)
        init(view)
        return view
    }

    private fun init(view: View) {

    }


    private fun getDialogTransaction(): MaterialDialog {

        val dialogAddNewTransaction = MaterialDialog.Builder(this.requireContext())
                .title(R.string.title_dialog_new_operation)
                .customView(R.layout.dialog_add_new_operation, true)
                .positiveText(R.string.ok)
                .onPositive { dialog, which ->
                    Toast.makeText(context, R.string.success_add_record, Toast.LENGTH_SHORT).show()
                }
                .negativeText(R.string.cancel)
                .build()

        dialogAddNewTransaction.customView?.apply {

            val categories = findViewById<Spinner>(R.id.categoriesSpinner)
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, context.resources.getStringArray(R.array.category))
            val incomeOutcomeSwitcher = findViewById<Switch>(R.id.incomeOutcomeSwitch)
            val inputDate = findViewById<EditText>(R.id.date)
            val amount = findViewById<EditText>(R.id.amount)

            inputDate.setText(Utils.formatDate(Calendar.getInstance().time))

            inputDate.setOnClickListener { v ->
                MaterialDialog.Builder(this.context)
                        .customView(R.layout.dialog_datepicker, false)
                        .positiveText(android.R.string.ok)
                        .onPositive { dialog, which ->
                            val calendarDialog = dialog.customView!!.findViewById<DatePicker>(R.id.datePicker)
                            val calendar = Calendar.getInstance()
                            calendar.set(calendarDialog.year, calendarDialog.month, calendarDialog.dayOfMonth)
                            inputDate.setText(Utils.formatDate(calendar.time))
                        }
                        .negativeText(android.R.string.cancel)
                        .show()
            }

            incomeOutcomeSwitcher.setText(R.string.outcome)
            incomeOutcomeSwitcher.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) incomeOutcomeSwitcher.setText(R.string.income)
                else incomeOutcomeSwitcher.setText(R.string.outcome)
            }
            categories.adapter = adapter
        }

        return dialogAddNewTransaction
    }



    companion object {
        fun newInstance(): BalanceFragment {
            val fragment = BalanceFragment()
            return fragment
        }
    }

}
