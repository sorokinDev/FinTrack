package com.mobilschool.fintrack.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.base.BaseFragment
import com.mobilschool.fintrack.ui.helper.WrapLinearLayoutManager
import com.mobilschool.fintrack.ui.main.MainPresenter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject
import androidx.core.view.ViewCompat.setNestedScrollingEnabled



class HomeFragment : BaseFragment(), HomeView {
    @Inject
    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    val balancesAdapter = BalancesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView(){
        appbar.replaceMenu(R.menu.main_menu)
        fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_transactionAddFragment))

        rv_balances.isNestedScrollingEnabled = false
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_balances.layoutManager = layoutManager

        rv_balances.adapter = balancesAdapter
        balancesAdapter.setData(arrayOf(Pair("RUB", "10.000"), Pair("USD", "5.000")))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    class BalancesAdapter: RecyclerView.Adapter<BalancesAdapter.ViewHolder>() {

        var items = arrayOf<Pair<String, String>>()

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvCurrency: TextView
            val tvBalance: TextView

            init {
                tvCurrency = itemView.findViewById(R.id.tv_currency)
                tvBalance = itemView.findViewById(R.id.tv_balance)
            }

            fun bind(balances: Pair<String, String>){
                tvCurrency.text = balances.first
                tvBalance.text = balances.second
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_balance_with_currency, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        fun setData(data: Array<Pair<String, String>>){
            items = data
            notifyDataSetChanged()
        }

    }


}
