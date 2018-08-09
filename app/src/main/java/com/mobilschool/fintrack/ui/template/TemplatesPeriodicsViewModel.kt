package com.mobilschool.fintrack.ui.template

import androidx.lifecycle.LiveData
import com.mobilschool.fintrack.data.entity.TemplateFull
import com.mobilschool.fintrack.data.interactor.WalletInteractor
import com.mobilschool.fintrack.ui.base.BaseViewModel
import javax.inject.Inject

class TemplatesPeriodicsViewModel @Inject constructor(
        val walletInteractor: WalletInteractor
): BaseViewModel() {

    val templates = walletInteractor.getAllTemplates()

    val periodics = walletInteractor.getAllPeriodics()

}
