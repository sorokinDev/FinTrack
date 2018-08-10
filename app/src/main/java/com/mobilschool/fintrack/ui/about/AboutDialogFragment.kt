package com.mobilschool.fintrack.ui.about

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mobilschool.fintrack.R


class AboutDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)

        builder.setTitle(getString(R.string.about))
                .setIcon(R.drawable.ic_info)
                .setView(R.layout.about_dialog_view)
                .setPositiveButton("Ok") { dialog, which -> }

        return builder.create()
    }
}