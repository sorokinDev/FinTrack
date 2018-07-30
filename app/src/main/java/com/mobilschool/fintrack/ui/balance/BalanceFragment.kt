package com.mobilschool.fintrack.ui.balance

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.newoperation.AddNewOperationDialogFragment
import com.mobilschool.fintrack.ui.newoperation.AddNewOperationPresenter
import com.razerdp.widget.animatedpieview.AnimatedPieView
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig
import com.razerdp.widget.animatedpieview.data.SimplePieInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*


class BalanceFragment : Fragment(), BalanceContract.View {

    override lateinit var presenter: BalanceContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_current_balance, container, false)

        presenter.getBalance(FinTrackerApplication.BASE_CURRENCY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t ->
                    view.findViewById<TextView>(R.id.currentBalanceBaseCurrency).text = t?.toString()
                }

        presenter.getBalance("usd")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t ->
                    view.findViewById<TextView>(R.id.currentBalanceAnyCurrency).text = t?.toString()
                }


        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val fragment = AddNewOperationDialogFragment()
            AddNewOperationPresenter(fragment)
            fragment.show(fragmentManager, "add_operation")
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

            presenter.getBalanceByCategories()
                    .onEach {

                        val tableRow1 = TableRow(context)

                        val textView1 = TextView(context)
                        textView1.setText(it.key)
                        textView1.gravity = Gravity.CENTER_HORIZONTAL
                        tableRow1.addView(textView1)

                        val textView2 = TextView(context)
                        textView2.setText(it.value.toString())
                        textView2.gravity = Gravity.CENTER_HORIZONTAL
                        tableRow1.addView(textView2)

                        val textView3 = TextView(context)
                        textView3.setText((it.value / balance!!).toString())
                        textView3.gravity = Gravity.CENTER_HORIZONTAL
                        tableRow1.addView(textView3)

                        table.addView(tableRow1)

                    }
                    .map {
                        SimplePieInfo(it.value / balance!!, Color.rgb(getRandomColor(), getRandomColor(), getRandomColor()), it.key)
                    }
                    .forEach { config.addData(it) }

            chart.applyConfig(config)
            chart.start()

        }

        return view
    }


    companion object {
        fun newInstance(/*, dialogPresenter: AddNewOperationContract.Presenter*/): BalanceFragment {
            val fragment = BalanceFragment()
            // fragment.operationDialogPresenter = dialogPresenter
            return fragment
        }
    }

}
