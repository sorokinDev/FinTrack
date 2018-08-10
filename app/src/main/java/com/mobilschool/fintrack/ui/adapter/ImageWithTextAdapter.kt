package com.mobilschool.fintrack.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.entity.Category

abstract class ImageWithTextAdapter<T>(context: Context, val spinnerLayoutId: Int, val dropdownLayoutId: Int = 0) : ArrayAdapter<T>(context, spinnerLayoutId){

    var data: List<T> = listOf()
        get
        set(value) {
            field = value
            clear()
            addAll(value)
            notifyDataSetChanged()
        }


    abstract fun getText(pos: Int): String
    abstract fun getImgRes(pos: Int): Int

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(spinnerLayoutId, parent, false)
        view.findViewById<TextView>(R.id.text).text = getText(position)
        view.findViewById<ImageView>(R.id.image).setImageResource(getImgRes(position))
        return view
    }



    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
                if( dropdownLayoutId != 0) dropdownLayoutId else spinnerLayoutId, parent, false)
        view.findViewById<TextView>(R.id.text).text = getText(position)
        view.findViewById<ImageView>(R.id.image).setImageResource(getImgRes(position))
        return view
    }

}