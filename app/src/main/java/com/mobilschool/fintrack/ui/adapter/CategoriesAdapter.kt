package com.mobilschool.fintrack.ui.adapter

import android.content.Context
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.entity.Category
import com.mobilschool.fintrack.data.source.local.entity.CategoryImgResConverter

class CategoriesAdapter(context: Context,
                        spinnerLayoutId: Int = R.layout.item_image_with_text,
                        dropdownLayoutId: Int = R.layout.item_image_with_text) : ImageWithTextAdapter<Category>(context, spinnerLayoutId, dropdownLayoutId) {

    override fun getText(pos: Int): String = data[pos].name

    override fun getImgRes(pos: Int): Int = CategoryImgResConverter.getDrawable(data[pos].imgRes)
}