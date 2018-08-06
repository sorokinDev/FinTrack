package com.mobilschool.fintrack.ui.home


import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.base.BaseFragment
import com.mobilschool.fintrack.ui.home.adapter.BalanceAdapter
import com.mobilschool.fintrack.ui.home.adapter.WalletAdapter
import com.mobilschool.fintrack.util.observe
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber


class HomeFragment : BaseFragment<HomeViewModel>() {

    lateinit var balanceInCurrenciesAdapter: BalanceAdapter

    lateinit var spinner_wallets: Spinner
    lateinit var walletsAdapter: WalletAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    fun initViews(){
        appbar.replaceMenu(R.menu.home_menu)

        initSpinnerWallets()
        initRvBalances()

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
                Timber.i("Hello")
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
            balanceInCurrenciesAdapter.wallet = it
        })

        viewModel.getBalanceInFavoriteCurrencies().observe(this, {
            if(it != null){
                balanceInCurrenciesAdapter.setData(it)
            }
        })

    }

    override fun getLayoutRes(): Int = R.layout.fragment_home
    override fun provideViewModel(): HomeViewModel = getViewModel(viewModelFactory)

}
