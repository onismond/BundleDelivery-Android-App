package com.pietech.bundledelivery.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.pietech.bundledelivery.R
import kotlinx.android.synthetic.main.dialog_accept_order.view.*

class DeliveryFeeDialog: DialogFragment() {

    internal lateinit var listener: FeeDialogListener

    interface  FeeDialogListener {
        fun onDialogConfirmClick(amount: String, dialog: Dialog)
//        fun onDialogCancelClick(dialog: DeliveryFeeDialog)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as FeeDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_accept_order, null)
            view.button_confirm.setOnClickListener {
                var amount = view.edit_text_amount.text.toString()
                if (!amount.isNullOrEmpty()) {
                    listener.onDialogConfirmClick(amount , dialog!!)
                }
            }
            view.button_cancel.setOnClickListener {
                dialog?.cancel()
            }

            builder.setView(view)
//                .setPositiveButton("Confirm",
//                    DialogInterface.OnClickListener{ dialog, id ->
//
//
//                    })
//                .setNegativeButton("Cancel",
//                    DialogInterface.OnClickListener { dialog, id ->
//
//                    })
            builder.create()
        } ?: throw java.lang.IllegalStateException("Activity cannot be null")
    }
}