package com.mobilschool.fintrack.ui.home


import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.base.BaseFragment
import com.mobilschool.fintrack.ui.home.adapter.BalanceAdapter
import com.mobilschool.fintrack.ui.home.adapter.TransactionAdapter
import com.mobilschool.fintrack.ui.transaction.add.adapter.TransactionAddBottomSheetFragment
import com.mobilschool.fintrack.ui.home.bottom_sheet.WalletsBottomSheetFragment
import com.mobilschool.fintrack.util.observe
import com.mobilschool.fintrack.util.toMoneyString
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<HomeViewModel>() {

    lateinit var balanceInCurrenciesAdapter: BalanceAdapter

    lateinit var transactionsAdapter: TransactionAdapter

    var isOtherVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        if(savedInstanceState == null){
            viewModel.executePendingTransactions()
        }
    }

    fun initViews(){
        app_bar_bottom.setNavigationOnClickListener {
            val bottomSheetWallets = WalletsBottomSheetFragment()
            bottomSheetWallets.show(fragmentManager, "bottom_sheet_wallets")
        }

        btn_new_transaction.setOnClickListener {
            val bottomSheetWallets = TransactionAddBottomSheetFragment.newInstance(viewModel.getSelectedWalletId().value!!)
            bottomSheetWallets.show(fragmentManager, "bottom_sheet_transaction_add")
        }


        initRvBalances()
        initRvTransactions()

    }


    private fun initRvBalances() {
        balanceInCurrenciesAdapter = BalanceAdapter()
        rv_balance_in_fav_currencies.adapter = balanceInCurrenciesAdapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_balance_in_fav_currencies.layoutManager = layoutManager
    }

    private fun initRvTransactions() {
        transactionsAdapter = TransactionAdapter()
        rv_transactions.adapter = transactionsAdapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_transactions.layoutManager = layoutManager
        //rv_transactions.addItemDecoration(DividerItemDecoration(rv_transactions.context, layoutManager.orientation))
    }

    private fun initObservers() {
        viewModel.getWallets().observe(this, { wallets ->
            if(wallets.isEmpty()) {
                // TODO: Do sth when there's no wallets
                return@observe
            }

            if(viewModel.getSelectedWalletId().value == null || wallets.firstOrNull { it.wallet.id == viewModel.getSelectedWalletId().value!! } == null) {
                viewModel.setSelectedWalletId(wallets[0].wallet.id)
            }
        })

        viewModel.getSelectedWallet().observe(this, { wallet ->
            appbar_top.setExpanded(true)
            rv_transactions.smoothScrollToPosition(0)
            tv_balance.text = "${wallet.wallet.balance.toMoneyString()} ${wallet.currencySymbol}"
            tv_current_wallet_name.text = wallet.wallet.name
        })

        viewModel.getTransactions().observe(this, { transactions ->
            transactionsAdapter.setData(viewModel.getSelectedWallet().value!!.wallet, transactions)
        })

        viewModel.getBalanceInFavoriteCurrencies().observe(this, {
            balanceInCurrenciesAdapter.setData(it)
        })
    }


    override fun getLayoutRes(): Int = R.layout.fragment_home
    override fun provideViewModel(): HomeViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(HomeViewModel::class.java)

}
