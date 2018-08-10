package com.mobilschool.fintrack.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.about.AboutDialogFragment
import com.mobilschool.fintrack.ui.base.BaseFragment
import com.mobilschool.fintrack.ui.home.adapter.BalanceAdapter
import com.mobilschool.fintrack.ui.home.adapter.TransactionAdapter
import com.mobilschool.fintrack.ui.home.adapter.WalletAdapter
import com.mobilschool.fintrack.ui.settings.SettingsActivity
import com.mobilschool.fintrack.util.observe
import com.mobilschool.fintrack.util.toMoneyString
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber


class HomeFragment : BaseFragment<HomeViewModel>() {

    lateinit var balanceInCurrenciesAdapter: BalanceAdapter

    lateinit var spinner_wallets: Spinner
    lateinit var walletsAdapter: WalletAdapter

    lateinit var transactionsAdapter: TransactionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        if(savedInstanceState == null){
            viewModel.executePendingTransactions()
        }
    }

    fun initViews(){
        appbar.replaceMenu(R.menu.home_menu)
        appbar.setOnMenuItemClickListener(){
            when (it.itemId) {
                R.id.about -> {
                    val aboutDialog = AboutDialogFragment()
                    aboutDialog.show(activity?.supportFragmentManager, "about_dialog")
                    true
                }
                else -> {
                    false
                }
            }
        }

        initSpinnerWallets()
        initRvBalances()
        initRvTransactions()

        fab_add.setOnClickListener {
            if(viewModel.getSelectedWalletId().value != null && viewModel.getSelectedWalletId().value!! > 0) {
                val navDir = HomeFragmentDirections.actionHomeFragmentToTransactionAddFragment(viewModel.getSelectedWalletId().value!!)
                Navigation.findNavController(it).navigate(navDir)
            }
        }
    }

    private fun initSpinnerWallets() {
        spinner_wallets = appbar.menu.findItem(R.id.spinner_wallets).actionView as Spinner
        walletsAdapter = WalletAdapter(requireContext())
        spinner_wallets.adapter = walletsAdapter
        spinner_wallets.setPopupBackgroundResource(android.R.color.white)
        spinner_wallets.setBackgroundResource(android.R.color.transparent)

        spinner_wallets.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                if(position >= 0 && walletsAdapter.data.size > position){
                    viewModel.setSelectedWalletId(walletsAdapter.data[position].id)
                }
            }

        }
    }

    private fun initRvBalances() {
        balanceInCurrenciesAdapter = BalanceAdapter()
        rv_balances.isNestedScrollingEnabled = false
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_balances.layoutManager = layoutManager
        rv_balances.adapter = balanceInCurrenciesAdapter
    }

    private fun initRvTransactions() {
        transactionsAdapter = TransactionAdapter()
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_transactions.layoutManager = layoutManager
        rv_transactions.adapter = transactionsAdapter
    }

    private fun initObservers() {
        viewModel.getWallets().observe(this, {
            walletsAdapter.data = it
        })

        viewModel.getSelectedWalletId().observe(this, {
            val pos = walletsAdapter.getPositionByWalletId(it)
            if(pos != spinner_wallets.selectedItemPosition) {
                spinner_wallets.setSelection(pos)
            }
        })

        viewModel.getSelectedWallet().observe(this, {
            tv_current_wallet.text = it.name
            tv_balance_in_main_currency.text = it.balance.toMoneyString()
            tv_main_currency.text = it.currency
            balanceInCurrenciesAdapter.wallet = it
        })

        viewModel.getBalanceInFavoriteCurrencies().observe(this, {
            if(it != null){
                balanceInCurrenciesAdapter.setData(it)
            }
        })

        viewModel.getTransactions().observe(this, {
            if(it.isEmpty()){
                layout_no_transactions.visibility = View.VISIBLE
                rv_transactions.visibility = View.GONE
            }else{
                layout_no_transactions.visibility = View.GONE
                rv_transactions.visibility = View.VISIBLE
                transactionsAdapter.setData(viewModel.getSelectedWallet().value!!, it)
            }
        })
    }





    override fun getLayoutRes(): Int = R.layout.fragment_home
    override fun provideViewModel(): HomeViewModel = getViewModel(viewModelFactory)

}
