package com.mobilschool.fintrack.ui.template.add

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.R.id.spinner_wallets
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import com.mobilschool.fintrack.ui.adapter.CategoriesAdapter
import com.mobilschool.fintrack.ui.adapter.WalletAdapter
import com.mobilschool.fintrack.ui.base.BaseBottomSheetFragment
import com.mobilschool.fintrack.ui.base.BaseDialogFragment
import com.mobilschool.fintrack.util.KeyboardUtil
import com.mobilschool.fintrack.util.observe
import kotlinx.android.synthetic.main.fragment_add_template.*
import timber.log.Timber

class TemplateAddFragment: BaseBottomSheetFragment<TemplateAddViewModel>() {
    override val showExpanded = true
    override val withRoundedCorners = true

    lateinit var walletsAdapter: WalletAdapter
    lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val ARG_IS_PERIODIC = "is_periodic"

        fun newInstance(isPeriodic: Boolean): TemplateAddFragment{
            return TemplateAddFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_PERIODIC, isPeriodic)
                }
            }
        }
    }

    override fun handleArguments() {
        if(arguments?.getBoolean(ARG_IS_PERIODIC) ?: false){
            viewModel.isPeriodic.value = true
        }
    }

    override fun initAdapters() {
        super.initAdapters()
        walletsAdapter = WalletAdapter(requireContext())
        spinner_wallets.adapter = walletsAdapter

        categoriesAdapter = CategoriesAdapter(requireContext())
        spinner_categories.adapter = categoriesAdapter
    }

    override fun initUI() {
        super.initUI()

        switch_periodic.setOnCheckedChangeListener { compoundButton, checked ->
            viewModel.isPeriodic.value = checked
        }

        rg_transaction_type.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.transactionType.value = when(i){
                R.id.rb_expense -> { Timber.i("EXPENCE RB") ; TransactionType.EXPENSE}
                R.id.rb_income -> { Timber.i("INCOME RB") ; TransactionType.INCOME }
                else -> { Timber.i("ELSE") ; TransactionType.EXPENSE }
            }
        }
        rg_transaction_type.check(R.id.rb_expense)

        btn_confirm_new_transaction.setOnClickListener {
            val amount = et_sum.text.toString().toDoubleOrNull() ?: -100.0
            if(amount <= 0){
                til_sum.error = getString(R.string.error_invalid_number)
                return@setOnClickListener
            }
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this@TemplateAddFragment.view?.windowToken, 0)
            Timber.i(viewModel.selectedCategoryId.value?.toString() ?: "NULL")
            if(viewModel.selectedCategoryId.value == null || viewModel.selectedCategoryId.value!! < 0){
                return@setOnClickListener
            }
            if(viewModel.isPeriodic.value!!){
                val period = et_period.text.toString().toIntOrNull() ?: -100
                if(period <= 0) {
                    til_period.error = getString(R.string.error_invalid_period)
                    return@setOnClickListener
                }
                viewModel.addPeriodicTemplate(amount, period)
            }else{
                viewModel.addTemplate(amount)
            }

            dismiss()
        }

        spinner_wallets.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                if(position >= 0 && walletsAdapter.data.size > position){
                    Timber.i("WALLET ID CHANGE")
                    viewModel.onWalletidChanged()
                }
            }
        }

        spinner_categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                if(position >= 0 && categoriesAdapter.data.size > position){
                    viewModel.selectedCategoryId.value = categoriesAdapter.data[position].id
                }
            }

        }
    }

    override fun initObservers() {
        super.initObservers()

        viewModel.isPeriodic.observe(this, {
            Timber.i("is periodic observer")
            switch_periodic.isChecked = it
            container_periodic_only.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })

        viewModel.wallets.observe(this, {
            walletsAdapter.data = it
            spinner_wallets.setSelection(0)
        })

        viewModel.categories.observe(this, {
            categoriesAdapter.data = it
            spinner_categories.setSelection(0)
            viewModel.selectedCategoryId.value = categoriesAdapter.data[0].id
        })

        viewModel.walletId.observe(this, { walId ->
            spinner_categories.setSelection(walletsAdapter.data.indexOfFirst { it.wallet.id == walId })
        })

        viewModel.wallet.observe(this, {
            Timber.i("WALLET observer")
        })
    }

    override fun getLayoutRes(): Int = R.layout.fragment_add_template

    override fun provideViewModel(): TemplateAddViewModel = getViewModel(viewModelFactory)
}