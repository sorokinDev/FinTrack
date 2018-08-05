package com.mobilschool.fintrack.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Transformations.map
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.ui.base.BaseFragment
import com.mobilschool.fintrack.ui.home.adapter.BalanceAdapter
import com.mobilschool.fintrack.ui.home.adapter.WalletAdapter
import com.mobilschool.fintrack.util.CurrencyAmountPair
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : BaseFragment(), HomeView {
    override fun setBalanceInCurrencies(walletInCurrencies: List<CurrencyAmountPair>) {
        balanceInCurrenciesAdapter.setData(
                walletInCurrencies.map { data -> Pair(data.first, data.second.toString()) }.toTypedArray()
        )
    }


    @Inject
    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    lateinit var balanceInCurrenciesAdapter: BalanceAdapter

    lateinit var spinner_wallets: Spinner
    lateinit var walletsAdapter: WalletAdapter

    fun initAdapters(){
        walletsAdapter = WalletAdapter(requireContext())
        balanceInCurrenciesAdapter = BalanceAdapter()
    }

    fun initView(){
        fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_transactionAddFragment))

        appbar.replaceMenu(R.menu.home_menu)

        spinner_wallets = appbar.menu.findItem(R.id.spinner_wallets).actionView as Spinner
        spinner_wallets.adapter = walletsAdapter
        spinner_wallets.setPopupBackgroundResource(android.R.color.white)
        spinner_wallets.setBackgroundResource(android.R.color.transparent)

        rv_balances.isNestedScrollingEnabled = false
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_balances.layoutManager = layoutManager
        rv_balances.adapter = balanceInCurrenciesAdapter

        spinner_wallets.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Timber.i(walletsAdapter.getItem(p2).name)

                presenter.selectWallet(walletsAdapter.getItem(p2))
            }
        }
    }

    override fun setWallets(it: List<Wallet>) {
        walletsAdapter.clear()
        walletsAdapter.addAll(it)
        walletsAdapter.notifyDataSetChanged()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapters()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
}
