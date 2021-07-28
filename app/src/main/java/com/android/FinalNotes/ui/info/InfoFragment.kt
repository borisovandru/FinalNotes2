package com.android.FinalNotes.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.FinalNotes.R
import com.android.FinalNotes.ui.info.AlertDialogFragment.Companion.newInstance
import com.google.android.material.snackbar.Snackbar

class InfoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.setFragmentResultListener(AlertDialogFragment.RESULT, viewLifecycleOwner, { requestKey, result -> Snackbar.make(requireView(), "Positive", Snackbar.LENGTH_SHORT).show() })
        view.findViewById<View>(R.id.dialog_fragment).setOnClickListener { showDialogFragment() }
        view.findViewById<View>(R.id.alert_dialog).setOnClickListener { showAlertDialog() }
        view.findViewById<View>(R.id.list_dialog).setOnClickListener { showListDialog() }
        view.findViewById<View>(R.id.single_choice_dialog).setOnClickListener { showSingleChoiceDialog() }
        view.findViewById<View>(R.id.multi_choice_dialog).setOnClickListener { showMultiChoiceDialog() }
        view.findViewById<View>(R.id.custom_view_dialog).setOnClickListener { showCustomViewDialog() }
        view.findViewById<View>(R.id.alert_dialog_fragment).setOnClickListener { showAlertDialogFragment() }
    }

    private fun showDialogFragment() {
        CustomDialogFragment.newInstance()
                .show(childFragmentManager, CustomDialogFragment.TAG)
    }

    private fun showAlertDialogFragment() {
        newInstance(R.string.list_title)
                .show(childFragmentManager, AlertDialogFragment.TAG)
    }

    private fun showCustomViewDialog() {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null, false)
        val editText = view.findViewById<EditText>(R.id.edit_name)
        val builder = AlertDialog.Builder(requireContext())
                .setTitle(R.string.notes_title)
                .setView(view)
                .setPositiveButton(R.string.select) { dialog, which -> Snackbar.make(requireView(), editText.text, Snackbar.LENGTH_SHORT).show() }
        builder.show()
    }

    private fun showMultiChoiceDialog() {
        val items = resources.getStringArray(R.array.options)
        val checked = BooleanArray(items.size)
        val builder = AlertDialog.Builder(requireContext())
                .setMultiChoiceItems(items, checked) { dialog, which, isChecked -> checked[which] = isChecked }
                .setPositiveButton(R.string.select) { dialog, which ->
                    val strBuilder = StringBuilder()
                    for (i in checked.indices) {
                        if (checked[i]) {
                            strBuilder.append(items[i])
                            strBuilder.append(',')
                        }
                    }
                    Snackbar.make(requireView(), strBuilder, Snackbar.LENGTH_SHORT).show()
                }
        builder.show()
    }

    private fun showSingleChoiceDialog() {
        val items = resources.getStringArray(R.array.options)
        val selected = intArrayOf(-1)
        val builder = AlertDialog.Builder(requireContext())
                .setSingleChoiceItems(items, selected[0]) { dialog, which -> selected[0] = which }
                .setPositiveButton(R.string.select) { dialog, which ->
                    if (selected[0] != -1) {
                        Snackbar.make(requireView(), items[selected[0]], Snackbar.LENGTH_SHORT).show()
                    }
                }
        builder.show()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setIcon(R.drawable.ic_clear)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_positive) { dialog, which -> Snackbar.make(requireView(), "Positive", Snackbar.LENGTH_SHORT).show() }
                .setNegativeButton(R.string.btn_negative) { dialog, which -> Snackbar.make(requireView(), "Negative", Snackbar.LENGTH_SHORT).show() }
                .setNeutralButton(R.string.btn_neutral) { dialog, which -> Snackbar.make(requireView(), "Neutral", Snackbar.LENGTH_SHORT).show() }
        builder.show()
    }

    private fun showListDialog() {
        val items = resources.getStringArray(R.array.options)
        val builder = AlertDialog.Builder(requireContext())
                .setTitle(R.string.list_title)
                .setItems(items) { dialog, which -> Snackbar.make(requireView(), items[which], Snackbar.LENGTH_SHORT).show() }
        builder.show()
    }

    companion object {
        const val TAG = "InfoFragment"
        @JvmStatic
        fun newInstance(): InfoFragment {
            return InfoFragment()
        }
    }
}