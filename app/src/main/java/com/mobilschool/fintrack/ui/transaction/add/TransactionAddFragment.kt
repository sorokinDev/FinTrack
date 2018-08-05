package com.mobilschool.fintrack.ui.transaction.add


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class TransactionAddFragment : BaseFragment(), TransactionAddView {

    @Inject
    @InjectPresenter
    lateinit var presenter: TransactionAddPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun initView(){
        appbar.replaceMenu(R.menu.home_menu)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_add, container, false)
    }


}
