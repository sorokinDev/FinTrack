package com.mobilschool.fintrack.ui.home.bottom_sheet

import android.os.Bundle
import android.view.View
import com.mobilschool.fintrack.R
import android.app.Dialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.data.source.local.entity.WalletTypeConverter
import com.mobilschool.fintrack.ui.base.BaseDialogFragment
import com.mobilschool.fintrack.ui.home.HomeViewModel
import com.mobilschool.fintrack.util.observe
import kotlinx.android.synthetic.main.fragment_bottom_sheet_wallets.*


class WalletsBottomSheetFragment : BaseDialogFragment<HomeViewModel>() {

    var localWallets = listOf<WalletFull>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), this.theme)
        // We want always expand dialog on start
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
        viewModel.getWallets().observe(this, { wallets ->
            val menu = nav_view_wallets.menu
            menu.clear()
            localWallets = wallets
            wallets.forEach { wallet ->
                val menuItem = menu.add(wallet.wallet.name)
                menuItem.setIcon(WalletTypeConverter.walletTypeToDrawableRes(wallet.wallet.type))
                menuItem.setCheckable(true)
                menuItem.setOnMenuItemClickListener {
                    viewModel.setSelectedWalletId(wallet.wallet.id)
                    this@WalletsBottomSheetFragment.dismiss()
                    true
                }
            }
        })

        viewModel.getSelectedWalletId().observe(this, { selWalletId ->
            val menuItem = nav_view_wallets.menu.getItem(localWallets.indexOfFirst { it.wallet.id == selWalletId })
            if(!menuItem.isChecked){
                menuItem.isChecked = true
            }
        })
    }

    override fun getLayoutRes(): Int = R.layout.fragment_bottom_sheet_wallets

    override fun provideViewModel(): HomeViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
}