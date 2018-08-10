package com.mobilschool.fintrack.ui.transaction.add

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import com.mobilschool.fintrack.ui.base.BaseDialogFragment
import com.mobilschool.fintrack.ui.adapter.CategoriesAdapter
import com.mobilschool.fintrack.ui.base.BaseBottomSheetFragment
import com.mobilschool.fintrack.util.KeyboardUtil
import com.mobilschool.fintrack.util.observe
import kotlinx.android.synthetic.main.fragment_bottom_sheet_transaction_add.*
import timber.log.Timber
import kotlin.math.roundToInt

class TransactionAddBottomSheetFragment: BaseBottomSheetFragment<TransactionAddViewModel>() {

    companion object {
        val ARG_SELECTED_WALLET_ID = "selected_wallet_id"
        val ARG_TEMPLATE = "template"
        fun newInstance(selWalletId: Int, templateId: Int): TransactionAddBottomSheetFragment {
            return  TransactionAddBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SELECTED_WALLET_ID, selWalletId)
                    putInt(ARG_TEMPLATE, templateId)
                }
            }
        }
    }

    override val showExpanded = true
    override val withRoundedCorners = true

    lateinit var categoriesAdapter: CategoriesAdapter

    override fun handleArguments() {
        super.handleArguments()
        if(arguments != null){
            viewModel.walletId.value = arguments!!.getInt(ARG_SELECTED_WALLET_ID)
            if(arguments!!.getInt(ARG_TEMPLATE) > 0){
                viewModel.templateId.value = arguments!!.getInt(ARG_TEMPLATE)
            }
        }
    }

    override fun initAdapters() {
        super.initAdapters()
        categoriesAdapter = CategoriesAdapter(requireContext())
        spinner_categories.adapter = categoriesAdapter
    }

    override fun initUI() {
        super.initUI()
        val sumTextWatcher = object : TextWatcher {
            override fun afterTextChanged(txt: Editable?) {
                val amount = txt.toString().toDoubleOrNull() ?: -100.0
                if(amount <= 0) {
                    til_sum.error = getString(R.string.error_invalid_number)
                }else{
                    til_sum.error = null
                    et_sum.removeTextChangedListener(this)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }


        spinner_categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                if(position >= 0 && categoriesAdapter.data.size > position){
                    viewModel.selectedCategoryId.value = categoriesAdapter.data[position].id
                }
            }

        }

        rg_transaction_type.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.transactionType.value = (when(i){
                R.id.rb_expense -> { Timber.i("EXPENCE RB") ; TransactionType.EXPENSE}
                R.id.rb_income -> { Timber.i("INCOME RB") ; TransactionType.INCOME }
                else -> { Timber.i("ELSE") ; TransactionType.EXPENSE }
            })
        }
        rg_transaction_type.check(R.id.rb_expense)

        btn_confirm_new_transaction.setOnClickListener {
            // TODO move this condition to ViewModel
            val amount = et_sum.text.toString().toDoubleOrNull() ?: -100.0
            if(amount <= 0){
                til_sum.error = getString(R.string.error_invalid_number)
                Toast.makeText(requireActivity(), R.string.error_invalid_number, Toast.LENGTH_SHORT).show()
                et_sum.addTextChangedListener(sumTextWatcher)
            }else{
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this@TransactionAddBottomSheetFragment.view?.windowToken, 0)
                Timber.i(viewModel.selectedCategoryId.value?.toString() ?: "NULL")
                if(viewModel.selectedCategoryId.value == null || viewModel.selectedCategoryId.value!! < 0){
                    Toast.makeText(requireContext(), R.string.error_select_category, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                Toast.makeText(requireActivity(), R.string.transaction_added, Toast.LENGTH_SHORT).show()
                viewModel.addTransaction(amount)
                dismiss()
            }
        }
    }

    override fun initObservers(){

        viewModel.wallet.observe(this, {
            tv_new_transaction_currency.text = it.currencySymbol
        })

        viewModel.transactionType.observe(this, {
            rg_transaction_type.check(
                    if(it == TransactionType.INCOME) R.id.rb_income
                    else R.id.rb_expense
            )
        })

        viewModel.categories.observe(this, {
            categoriesAdapter.data = it
            spinner_categories.setSelection(0)
            viewModel.selectedCategoryId.value = categoriesAdapter.data[0].id
        })

        viewModel.template.observe(this, { tpl ->
            viewModel.walletId.value = tpl.transaction.walletId
            viewModel.transactionType.value = tpl.transaction.type
            viewModel.selectedCategoryId.value = tpl.transaction.categoryId
            et_sum.setText(tpl.transaction.amount.roundToInt().toString())
        })
    }

    override fun getLayoutRes(): Int = R.layout.fragment_bottom_sheet_transaction_add

    override fun provideViewModel(): TransactionAddViewModel = getViewModel(viewModelFactory)
}