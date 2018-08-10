package com.mobilschool.fintrack.ui.home


import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.base.BaseFragment
import com.mobilschool.fintrack.ui.adapter.BalanceAdapter
import com.mobilschool.fintrack.ui.adapter.TemplateAdapter
import com.mobilschool.fintrack.ui.adapter.TransactionAdapter
import com.mobilschool.fintrack.ui.transaction.add.TransactionAddBottomSheetFragment
import com.mobilschool.fintrack.ui.home.bottom_sheet.WalletsBottomSheetFragment
import com.mobilschool.fintrack.util.observe
import com.mobilschool.fintrack.util.toMoneyString
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<HomeViewModel>() {



    lateinit var balanceInCurrenciesAdapter: BalanceAdapter
    lateinit var transactionsAdapter: TransactionAdapter
    lateinit var templatesAdapter: TemplateAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState == null){
            viewModel.executePendingTransactions()
        }
    }

    override fun initAdapters() {
        super.initAdapters()
        balanceInCurrenciesAdapter = BalanceAdapter()
        rv_balance_in_fav_currencies.adapter = balanceInCurrenciesAdapter
        rv_balance_in_fav_currencies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        transactionsAdapter = TransactionAdapter()
        rv_transactions.adapter = transactionsAdapter
        rv_transactions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        //rv_transactions.addItemDecoration(DividerItemDecoration(rv_transactions.context, layoutManager.orientation))

        templatesAdapter = TemplateAdapter()
        rv_templates.adapter = templatesAdapter
        rv_templates.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        templatesAdapter.onClick = { templateId ->
            val bottomSheetWallets = TransactionAddBottomSheetFragment.newInstance(viewModel.selectedWalletId.value!!, templateId)
            bottomSheetWallets.show(fragmentManager, "bottom_sheet_transaction_add")
        }
    }

    override fun initUI() {
        super.initUI()
        app_bar_bottom.setNavigationOnClickListener {
            val bottomSheetWallets = WalletsBottomSheetFragment()
            bottomSheetWallets.show(fragmentManager, "bottom_sheet_wallets")
        }

        btn_new_transaction.setOnClickListener {
            val bottomSheetWallets = TransactionAddBottomSheetFragment.newInstance(viewModel.selectedWalletId.value!!, -1)
            bottomSheetWallets.show(fragmentManager, "bottom_sheet_transaction_add")
        }
    }

    override fun initObservers() {
        viewModel.wallets.observe(this, { wallets ->
            if(wallets.isEmpty()) {
                return@observe
            }
            if(viewModel.selectedWalletId.value == null || wallets.firstOrNull { it.wallet.id == viewModel.selectedWalletId.value!! } == null) {
                viewModel.selectedWalletId.value = wallets[0].wallet.id
            }
        })

        viewModel.selectedWallet.observe(this, { wallet ->
            appbar_top?.setExpanded(true)
            rv_transactions.smoothScrollToPosition(0)
            tv_balance.text = "${wallet.wallet.balance.toMoneyString()} ${wallet.currencySymbol}"
            tv_current_wallet_name.text = wallet.wallet.name
        })

        viewModel.transactions.observe(this, { transactions ->
            transactionsAdapter.setData(viewModel.selectedWallet.value!!.wallet, transactions)
            if(transactions.isEmpty()){
                content_no_data.visibility = View.VISIBLE
                rv_transactions.visibility = View.GONE
            }else{
                content_no_data.visibility = View.GONE
                rv_transactions.visibility = View.VISIBLE
            }
        })

        viewModel.balanceInFavoriteCurrencies.observe(this, {
            balanceInCurrenciesAdapter.setData(it)
        })

        viewModel.templates.observe(this, {
            templatesAdapter.items = it
        })
    }


    override fun getLayoutRes(): Int = R.layout.fragment_home
    override fun provideViewModel(): HomeViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(HomeViewModel::class.java)


}
