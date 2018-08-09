package com.mobilschool.fintrack.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.entity.TransactionFull
import com.mobilschool.fintrack.data.source.local.entity.CategoryImgResConverter
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.util.toMoneyString

class TransactionAdapter: RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    lateinit var wallet: Wallet
    var items = listOf<TransactionFull>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSum: TextView
        val tvCategory: TextView
        val ivCategory: ImageView

        init {
            tvCategory = itemView.findViewById(R.id.tv_trans_target)
            tvSum = itemView.findViewById(R.id.tv_trans_sum)
            ivCategory = itemView.findViewById(R.id.iv_trans_category)
        }

        fun bind(item: TransactionFull){
            tvCategory.text = item.categoryName
            tvSum.text = "${item.transaction.amount.toMoneyString()} ${item.currencySymbol}"
            ivCategory.setImageResource(CategoryImgResConverter.getDrawable(item.categoryImgRes))

            val textColor = ResourcesCompat.getColor(tvCategory.resources, if(item.transaction.type == TransactionType.EXPENSE) { R.color.colorExpense } else { R.color.colorIncome }, null)
            tvCategory.setTextColor(textColor)
            tvSum.setTextColor(textColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(wallet: Wallet, data: List<TransactionFull>){
        this.wallet = wallet
        items = data
        notifyDataSetChanged()
    }

}