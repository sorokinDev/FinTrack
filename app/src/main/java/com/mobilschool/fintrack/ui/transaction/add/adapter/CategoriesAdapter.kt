package com.mobilschool.fintrack.ui.transaction.add.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.entity.Category

class CategoriesAdapter(context: Context)
    : ArrayAdapter<Category>(context, R.layout.item_category_spinner) {

    var data: List<Category> = listOf()
            get
            set(value) {
                field = value
                clear()
                addAll(value)
                notifyDataSetChanged()
            }

    init {
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val transactionCategory = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_category_spinner, parent, false)
        view.findViewById<TextView>(android.R.id.text1).text = transactionCategory.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val transactionCategory = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_category_dropdown, parent, false)
        view.findViewById<TextView>(android.R.id.text1).text = transactionCategory.name
        return view
    }

    fun getPositionByCategoryId(id: Int): Int {
        return data.indexOfFirst { it.id == id }
    }


}