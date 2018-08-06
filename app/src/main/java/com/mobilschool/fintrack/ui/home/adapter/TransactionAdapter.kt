package com.mobilschool.fintrack.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransaction
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransactionWithCategory
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.util.CurrencyAmountPair
import com.mobilschool.fintrack.util.toMoney
import com.mobilschool.fintrack.util.toMoneyString

class TransactionAdapter: RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    lateinit var wallet: Wallet
    var items = listOf<MoneyTransactionWithCategory>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCurrency: TextView
        val tvSum: TextView
        val tvTarget: TextView

        init {
            tvTarget = itemView.findViewById(R.id.tv_trans_target)
            tvSum = itemView.findViewById(R.id.tv_trans_sum)
            tvCurrency = itemView.findViewById(R.id.tv_trans_currency)
        }

        fun bind(wallet: Wallet, item: MoneyTransactionWithCategory){
            tvTarget.text = item.categoryName
            tvSum.text = item.amount.toMoneyString()
            tvCurrency.text = wallet.currency

            val textColor = ResourcesCompat.getColor(tvTarget.resources, if(item.type == TransactionType.EXPENSE) { R.color.colorExpense } else { R.color.colorIncome }, null)
            tvTarget.setTextColor(textColor)
            tvSum.setTextColor(textColor)
            tvCurrency.setTextColor(textColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wallet, items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(wallet: Wallet, data: List<MoneyTransactionWithCategory>){
        this.wallet = wallet
        items = data
        notifyDataSetChanged()
    }

}