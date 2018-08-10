package com.mobilschool.fintrack.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.entity.TemplateFull
import com.mobilschool.fintrack.data.entity.TransactionFull
import com.mobilschool.fintrack.data.source.local.entity.CategoryImgResConverter
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import com.mobilschool.fintrack.util.toMoneyString

class TemplateAdapter: RecyclerView.Adapter<TemplateAdapter.ViewHolder>() {
    lateinit var onClick: (templateId: Int) -> Unit

    var items = listOf<TemplateFull>()
        get
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSum: TextView
        val tvCategory: TextView
        val ivCategory: ImageView

        init {
            tvCategory = itemView.findViewById(R.id.tv_template_title)
            tvSum = itemView.findViewById(R.id.tv_template_amount)
            ivCategory = itemView.findViewById(R.id.iv_template_icon)
        }

        fun bind(item: TemplateFull){
            tvCategory.text = item.categoryName
            tvSum.text = "${item.transaction.amount.toMoneyString()} ${item.currencySymbol}"
            ivCategory.setImageResource(CategoryImgResConverter.getDrawable(item.categoryImgRes))

            val textColor = ResourcesCompat.getColor(tvCategory.resources, if(item.transaction.type == TransactionType.EXPENSE) { R.color.colorExpense } else { R.color.colorIncome }, null)
            tvSum.setTextColor(textColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_template_in_home, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            if(holder.adapterPosition != RecyclerView.NO_POSITION){
                onClick(items[holder.adapterPosition].transaction.id)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}