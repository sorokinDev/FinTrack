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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobilschool.fincalc.Operations
import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.util.Utils
import com.razerdp.widget.animatedpieview.AnimatedPieView
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig
import com.razerdp.widget.animatedpieview.data.SimplePieInfo
import java.text.SimpleDateFormat
import java.util.*


class BalanceFragment : Fragment(), BalanceContract.View {


    override var presenter: BalanceContract.Presenter = BalancePresenter(null, null)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_current_balance, container, false)
        presenter.attachView(this)
        init(view)
        return view
    }

    private fun init(view: View) {

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            getDialogTransaction().show()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.findViewById<ScrollView>(R.id.scrollContainer).setOnScrollChangeListener { p0, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > oldScrollY) {
                    fab.hide()
                } else {
                    fab.show()
                }
            }
        }

        presenter.getBalance(FinTrackerApplication.BASE_CURRENCY)
                .subscribe { t ->
                    view.findViewById<TextView>(R.id.currentBalanceBaseCurrency).text = String.format("%.2f", t)
                }

        presenter.getBalance("usd")
                .subscribe { t ->
                    view.findViewById<TextView>(R.id.currentBalanceAnyCurrency).text = String.format("%.2f", t)
                }

        presenter.getBalance(FinTrackerApplication.BASE_CURRENCY).subscribe { balance ->

            val random = Random()
            val getRandomColor = { random.nextInt(256) }

            val chart = view.findViewById<AnimatedPieView>(R.id.chart)
            val config = AnimatedPieViewConfig()
            config.startAngle(-90f)
                    .strokeMode(false)
                    .splitAngle(1f)
                    .drawText(true)
                    .textSize(35f)
                    .duration(2000)

            val table = view.findViewById<TableLayout>(R.id.table)


            presenter.getSumWithoutIncomeOutcome().subscribe { sum ->

                presenter.getBalanceByCategories()
                        .forEach {
                            val titleRow = TableRow(context)

                            val categoryColumn = TextView(context)
                            categoryColumn.setText(it.first)
                            categoryColumn.gravity = Gravity.CENTER_HORIZONTAL
                            titleRow.addView(categoryColumn)

                            val amountColumn = TextView(context)
                            amountColumn.setText(it.second.toString())
                            amountColumn.gravity = Gravity.CENTER_HORIZONTAL
                            titleRow.addView(amountColumn)

                            val percentColumn = TextView(context)
                            percentColumn.setText(String.format("%.2f", (it.second / sum) * 100))
                            percentColumn.gravity = Gravity.CENTER_HORIZONTAL
                            titleRow.addView(percentColumn)

                            table.addView(titleRow)

                            val pieInfo = SimplePieInfo(it.second / sum, Color.rgb(getRandomColor(), getRandomColor(), getRandomColor()), it.first)
                            config.addData(pieInfo)
                        }
            }

            chart.applyConfig(config)
            chart.start()
        }
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


    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    companion object {
        fun newInstance(/*, dialogPresenter: AddNewOperationContract.Presenter*/): BalanceFragment {
            val fragment = BalanceFragment()
            // fragment.operationDialogPresenter = dialogPresenter
            return fragment
        }
    }

}
