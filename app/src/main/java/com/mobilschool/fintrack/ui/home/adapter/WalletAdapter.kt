package com.mobilschool.fintrack.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.data.source.local.entity.WalletType
import com.mobilschool.fintrack.data.source.local.entity.WalletTypeConverter

class WalletAdapter(context: Context?)
    : ArrayAdapter<Wallet>(context, R.layout.item_wallet_small) {

    var data: List<Wallet> = listOf<Wallet>()
            get
            set(value) {
                field = value
                clear()
                addAll(value)
                notifyDataSetChanged()
            }

    init {
        setDropDownViewResource(R.layout.item_wallet_dropdown)
    }

    private fun getDrawableResForWallet(wallet: Wallet): Int = WalletTypeConverter.walletTypeToDrawableRes(wallet.type)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val wallet = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_wallet_small, parent, false)
        view.findViewById<ImageView>(R.id.iv_wallet).setImageResource(getDrawableResForWallet(wallet))
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val wallet = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_wallet_dropdown, parent, false)
        view.findViewById<ImageView>(R.id.iv_wallet).setImageResource(getDrawableResForWallet(wallet))
        view.findViewById<TextView>(R.id.tv_wallet_name).text = wallet.name
        return view
    }

    fun getPositionByWalletId(id: Int): Int {
        return data.indexOfFirst { it.id == id }
    }


}