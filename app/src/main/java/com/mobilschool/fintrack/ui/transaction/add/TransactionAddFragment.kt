package com.mobilschool.fintrack.ui.transaction.add


import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.Navigation
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransaction
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import com.mobilschool.fintrack.ui.base.BaseFragment
import com.mobilschool.fintrack.ui.home.HomeFragmentDirections
import com.mobilschool.fintrack.util.observe
import kotlinx.android.synthetic.main.fragment_transaction_add.*

class TransactionAddFragment : BaseFragment<TransactionAddViewModel>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = TransactionAddFragmentArgs.fromBundle(arguments)
        viewModel.setWalletId(args.walletId)
        initRadioGroupType()
        initViews()
        initObservers()
    }


    fun initRadioGroupType(){
        rb_expense.setOnCheckedChangeListener { compoundButton, checked ->
            if(checked){
                compoundButton.typeface = Typeface.DEFAULT_BOLD
            }else{
                compoundButton.typeface = Typeface.DEFAULT
            }
        }

        rb_income.setOnCheckedChangeListener { compoundButton, checked ->
            if(checked){
                compoundButton.typeface = Typeface.DEFAULT_BOLD
            }else{
                compoundButton.typeface = Typeface.DEFAULT
            }
        }
        rg_transaction_type.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.setTransactionType(when(i){
                R.id.rb_expense -> TransactionType.EXPENSE
                R.id.rb_income -> TransactionType.INCOME
                else -> TransactionType.EXPENSE
            })
        }

        rg_transaction_type.check(R.id.rb_expense)
    }

    private fun initViews() {
        val sumTextWatcher = object : TextWatcher{
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

        fab_confirm.setOnClickListener {
            val amount = et_sum.text.toString().toDoubleOrNull() ?: -100.0
            if(amount <= 0){
                til_sum.error = getString(R.string.error_invalid_number)
                Toast.makeText(requireActivity(), R.string.error_invalid_number, Toast.LENGTH_SHORT).show()
                et_sum.addTextChangedListener(sumTextWatcher)
            }else{
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this@TransactionAddFragment.view?.windowToken, 0)
                Toast.makeText(requireActivity(), "Transaction added", Toast.LENGTH_SHORT).show()
                viewModel.addTransaction(amount)
                val navDir = TransactionAddFragmentDirections.actionTransactionAddFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(navDir)

            }
        }
    }

    private fun initObservers() {
        viewModel.getWallet().observe(this, {
            tv_wallet.text = it.name
            tv_transaction_currency.text = it.currency
        })

        viewModel.getTransactionType().observe(this, {
            rg_transaction_type.check(when(it){
                TransactionType.EXPENSE -> R.id.rb_expense
                TransactionType.INCOME -> R.id.rb_income
            })
        })

    }

    override fun getLayoutRes(): Int = R.layout.fragment_transaction_add

    override fun provideViewModel(): TransactionAddViewModel = getViewModel(viewModelFactory)


}
