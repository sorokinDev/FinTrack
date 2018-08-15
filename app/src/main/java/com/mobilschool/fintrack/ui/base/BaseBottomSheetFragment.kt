package com.mobilschool.fintrack.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.util.KeyboardUtil

abstract class BaseBottomSheetFragment<T : BaseViewModel>: BaseDialogFragment<T>() {

    abstract val showExpanded: Boolean
    abstract val withRoundedCorners: Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        KeyboardUtil(requireActivity(), view!!)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), this.theme)
        dialog.setOnShowListener { dial ->
            val d = dial as BottomSheetDialog
            val bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            if(withRoundedCorners) {
                bottomSheet!!.setBackgroundResource(R.drawable.rounded_rect)
            }
            if(showExpanded) {
                BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

}