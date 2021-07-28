package com.android.FinalNotes.ui.info

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.android.FinalNotes.R

class AlertDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        assert(arguments != null)
        val builder = AlertDialog.Builder(requireContext())
                .setTitle(requireArguments().getInt(ARG_TITLE))
                .setMessage(R.string.alert_dialog_message)
                .setIcon(R.drawable.ic_clear)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_positive) { _: DialogInterface?, which: Int -> setFragmentResult() }
        return builder.create()
    }

    companion object {
        const val TAG = "AlertDialogFragment"
        const val RESULT = "AlertDialogFragment"
        private const val ARG_TITLE = "ARG_TITLE"
        @JvmStatic
        fun newInstance(@StringRes title: Int): AlertDialogFragment {
            val alertDialogFragment = AlertDialogFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_TITLE, title)
            alertDialogFragment.arguments = bundle
            return alertDialogFragment
        }
    }
}

private fun setFragmentResult() {

}
