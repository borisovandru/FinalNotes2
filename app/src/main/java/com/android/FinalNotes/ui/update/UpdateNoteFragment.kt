package com.android.FinalNotes.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.android.FinalNotes.R
import com.android.FinalNotes.RouterHolder
import com.android.FinalNotes.domain.Note
import com.android.FinalNotes.domain.NotesFirestoreRepository
import java.util.*

class UpdateNoteFragment : Fragment() {
    private val repository = NotesFirestoreRepository.INSTANCE
    private var selectedYear = -1
    private var selectedMonthOfYear = -1
    private var selectedDayOfMonth = -1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assert(arguments != null)
        val note: Note? = requireArguments().getParcelable(ARG_NOTE)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val title = view.findViewById<EditText>(R.id.title)
        toolbar.setOnMenuItemClickListener { item: MenuItem ->
            if (item.itemId == R.id.action_done) {
                var selectedDate: Date? = null
                if (selectedYear != -1 && selectedDayOfMonth != -1 && selectedMonthOfYear != -1) {
                    val calendar = Calendar.getInstance()
                    if (note != null) {
                        calendar.time = note.date
                    }
                    calendar[Calendar.YEAR] = selectedYear
                    calendar[Calendar.MONTH] = selectedMonthOfYear
                    calendar[Calendar.DAY_OF_MONTH] = selectedDayOfMonth
                    selectedDate = calendar.time
                }
                val result = note?.let { repository.update(it, title.text.toString(), selectedDate) }
                if (requireActivity() is RouterHolder) {
                    val router = (requireActivity() as RouterHolder).mainRouter
                    val bundle = Bundle()
                    bundle.putParcelable(ARG_NOTE, result)
                    parentFragmentManager.setFragmentResult(UPDATE_RESULT, bundle)
                    router!!.back()
                }
                return@setOnMenuItemClickListener true
            }
            false
        }
        if (note != null) {
            title.setText(note.title)
        }
        val datePicker = view.findViewById<DatePicker>(R.id.picker)
        val calendar = Calendar.getInstance()
        if (note != null) {
            calendar.time = note.date
        }
        datePicker.init(calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]) { view1: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            selectedYear = year
            selectedMonthOfYear = monthOfYear
            selectedDayOfMonth = dayOfMonth
        }
    }

    companion object {
        const val TAG = "UpdateNoteFragment"
        const val UPDATE_RESULT = "UPDATE_RESULT"
        const val ARG_NOTE = "ARG_NOTE"
        @JvmStatic
        fun newInstance(note: Note?): UpdateNoteFragment {
            val fragment = UpdateNoteFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_NOTE, note)
            fragment.arguments = bundle
            return fragment
        }
    }
}