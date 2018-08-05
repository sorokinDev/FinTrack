package com.mobilschool.fintrack.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobilschool.fintrack.R

class BalanceAdapter: RecyclerView.Adapter<BalanceAdapter.ViewHolder>() {

    var items = arrayOf<Pair<String, String>>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCurrency: TextView
        val tvBalance: TextView

        init {
            tvCurrency = itemView.findViewById(R.id.tv_currency)
            tvBalance = itemView.findViewById(R.id.tv_balance)
        }

        fun bind(balances: Pair<String, String>){
            tvCurrency.text = balances.first
            tvBalance.text = balances.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_balance_with_currency, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(data: Array<Pair<String, String>>){
        items = data
        notifyDataSetChanged()
    }

}