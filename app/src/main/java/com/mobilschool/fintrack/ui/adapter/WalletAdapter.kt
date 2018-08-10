package com.mobilschool.fintrack.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.data.source.local.entity.WalletTypeConverter

class WalletAdapter(context: Context,
                        spinnerLayoutId: Int = R.layout.item_image_with_text,
                        dropdownLayoutId: Int = R.layout.item_image_with_text) : ImageWithTextAdapter<WalletFull>(context, spinnerLayoutId, dropdownLayoutId) {

    override fun getText(pos: Int): String = data[pos].wallet.name

    override fun getImgRes(pos: Int): Int = WalletTypeConverter.walletTypeToDrawableRes(data[pos].wallet.type)
}