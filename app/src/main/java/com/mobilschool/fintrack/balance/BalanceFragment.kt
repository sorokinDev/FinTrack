package com.mobilschool.fintrack.CurrentBalance

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobilschool.fintrack.AddNewOperationDialogFragment
import com.mobilschool.fintrack.R
import com.razerdp.widget.animatedpieview.AnimatedPieView
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig
import com.razerdp.widget.animatedpieview.data.SimplePieInfo
import java.util.*


class BalanceFragment : Fragment(), BalanceContract.View {

    override lateinit var presenter: BalanceContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_current_balance, container, false)

        view.findViewById<TextView>(R.id.currentBalanceBaseCurrency).text = presenter.getBalance("rub").toString()
        view.findViewById<TextView>(R.id.currentBalanceAnyCurrency).text = presenter.getBalance("usd").toString()
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            AddNewOperationDialogFragment().show(fragmentManager, "add_operation")
        }

        val balance = presenter.getBalance("rub")

        val chart = view.findViewById<AnimatedPieView>(R.id.chart)
        val config = AnimatedPieViewConfig()
        config.startAngle(-90f)
                .strokeMode(false)
                .splitAngle(1f)
                .drawText(true)
                .textSize(35f)
                .duration(2000)

        val random = Random()
        val getRandomColor = { random.nextInt(256) }
        presenter.getBalanceByCategories()
                .map { SimplePieInfo(it.value / balance, Color.rgb(getRandomColor(), getRandomColor(), getRandomColor()), it.key) }
                .forEach { config.addData(it) }

        chart.applyConfig(config)
        chart.start()

        return view
    }


    companion object {
        fun newInstance(presenter: BalanceContract.Presenter): BalanceFragment {
            val fragment = BalanceFragment()
            fragment.presenter = presenter
            return fragment
        }
    }

}
