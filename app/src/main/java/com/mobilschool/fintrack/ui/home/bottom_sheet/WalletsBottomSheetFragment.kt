package com.mobilschool.fintrack.ui.home.bottom_sheet

import android.os.Bundle
import android.view.View
import com.mobilschool.fintrack.R
import android.app.Dialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.data.source.local.entity.WalletTypeConverter
import com.mobilschool.fintrack.ui.about.AboutDialogFragment
import com.mobilschool.fintrack.ui.base.BaseBottomSheetFragment
import com.mobilschool.fintrack.ui.base.BaseDialogFragment
import com.mobilschool.fintrack.ui.home.HomeViewModel
import com.mobilschool.fintrack.util.observe
import kotlinx.android.synthetic.main.fragment_bottom_sheet_wallets.*


class WalletsBottomSheetFragment : BaseBottomSheetFragment<HomeViewModel>() {


    override val showExpanded= true
    override val withRoundedCorners = true

    var localWallets = listOf<WalletFull>()

    override fun initUI() {
        super.initUI()
        nav_view_home.menu.findItem(R.id.nav_templates_periodics).setOnMenuItemClickListener {
            Navigation.findNavController(parentFragment?.view!!).navigate(R.id.action_homeFragment_to_templatesPeriodicsFragment)
            this@WalletsBottomSheetFragment.dismiss()
            true
        }

        nav_view_home.menu.findItem(R.id.about).setOnMenuItemClickListener {
            AboutDialogFragment().show(fragmentManager, "about")
            this@WalletsBottomSheetFragment.dismiss()
            true
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.wallets.observe(this, { wallets ->
            val menu = nav_view_wallets.menu
            menu.clear()
            localWallets = wallets
            wallets.forEach { wallet ->
                val menuItem = menu.add(wallet.wallet.name)
                menuItem.setIcon(WalletTypeConverter.walletTypeToDrawableRes(wallet.wallet.type))
                menuItem.setCheckable(true)
                menuItem.setOnMenuItemClickListener {
                    viewModel.selectedWalletId.value = wallet.wallet.id
                    this@WalletsBottomSheetFragment.dismiss()
                    true
                }
            }
        })

        viewModel.selectedWalletId.observe(this, { selWalletId ->
            val menuItem = nav_view_wallets.menu.getItem(localWallets.indexOfFirst { it.wallet.id == selWalletId })
            if(!menuItem.isChecked){
                menuItem.isChecked = true
            }
        })
    }

    override fun getLayoutRes(): Int = R.layout.fragment_bottom_sheet_wallets
    override fun provideViewModel(): HomeViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
}