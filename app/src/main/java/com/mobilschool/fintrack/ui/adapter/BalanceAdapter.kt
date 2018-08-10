package com.mobilschool.fintrack.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.util.CurrencyAmountPair
import com.mobilschool.fintrack.util.toMoney
import com.mobilschool.fintrack.util.toMoneyString

class BalanceAdapter: RecyclerView.Adapter<BalanceAdapter.ViewHolder>() {
    var wallet: Wallet? = null
    var items = listOf<CurrencyAmountPair>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCurrency: TextView
        val tvBalance: TextView

        init {
            tvCurrency = itemView.findViewById(R.id.tv_currency)
            tvBalance = itemView.findViewById(R.id.tv_in_other_currencies)
        }

        fun bind(walletBalance: Double, balance: CurrencyAmountPair){
            tvCurrency.text = balance.first.id
            val balanceAsMoney = balance.second.toMoney()
            if(balanceAsMoney != 0.0 || walletBalance == 0.0){
                tvBalance.text = "${balanceAsMoney.toMoneyString()} ${balance.first.symbol}"
            }else{
                tvBalance.text = "---"
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_balance_with_currency, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wallet?.balance ?: 0.0, items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(data: List<CurrencyAmountPair>){
        items = data
        notifyDataSetChanged()
    }

}