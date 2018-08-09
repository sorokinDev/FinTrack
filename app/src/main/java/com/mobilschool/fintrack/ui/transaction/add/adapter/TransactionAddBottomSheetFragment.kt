package com.mobilschool.fintrack.ui.transaction.add.adapter

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
import com.mobilschool.fintrack.util.KeyboardUtil
import com.mobilschool.fintrack.util.observe
import kotlinx.android.synthetic.main.fragment_bottom_sheet_transaction_add.*
import timber.log.Timber

class TransactionAddBottomSheetFragment: BaseDialogFragment<TransactionAddViewModel>() {

    lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        val ARG_SELECTED_WALLET_ID = "selected_wallet_id"
        fun newInstance(selWalletId: Int): TransactionAddBottomSheetFragment {
            return  TransactionAddBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SELECTED_WALLET_ID, selWalletId)
                }
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        KeyboardUtil(requireActivity(), view!!)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), this.theme)
        dialog.setOnShowListener { dial ->
            val d = dial as BottomSheetDialog
            val bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            bottomSheet!!.setBackgroundResource(R.drawable.rounded_rect)
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null){
            viewModel.setWalletId(arguments!!.getInt(ARG_SELECTED_WALLET_ID))
        }

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

        categoriesAdapter = CategoriesAdapter(requireContext())
        spinner_categories.adapter = categoriesAdapter

        spinner_categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                if(position >= 0 && categoriesAdapter.data.size > position){
                    viewModel.setSelectedCategoryId(categoriesAdapter.data[position].id)
                }
            }

        }

        rg_transaction_type.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.setTransactionType(when(i){
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
                Timber.i(viewModel.getSelectedCategoryId().value?.toString() ?: "NULL")
                if(viewModel.getSelectedCategoryId().value == null || viewModel.getSelectedCategoryId().value!! < 0){
                    Toast.makeText(requireContext(), R.string.error_select_category, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                Toast.makeText(requireActivity(), R.string.transaction_added, Toast.LENGTH_SHORT).show()
                viewModel.addTransaction(amount)
                dismiss()
            }
        }

        initObservers()
    }


    fun initObservers(){
        viewModel.getWallet().observe(this, {
            tv_new_transaction_currency.text = it.wallet.currencyId
        })

        viewModel.getTransactionType().observe(this, {
            rg_transaction_type.check(
                    if(it == TransactionType.INCOME) R.id.rb_income
                    else R.id.rb_expense
            )
        })

        viewModel.getCategories().observe(this, {
            categoriesAdapter.data = it
            spinner_categories.setSelection(0)
            viewModel.setSelectedCategoryId(categoriesAdapter.data[0].id)
        })
    }

    override fun getLayoutRes(): Int = R.layout.fragment_bottom_sheet_transaction_add

    override fun provideViewModel(): TransactionAddViewModel = getViewModel(viewModelFactory)
}