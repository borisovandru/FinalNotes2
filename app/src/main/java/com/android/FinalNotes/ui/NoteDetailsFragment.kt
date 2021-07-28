package com.android.FinalNotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.FinalNotes.R
import com.android.FinalNotes.domain.Note

class NoteDetailsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_not_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<TextView>(R.id.title)
        assert(arguments != null)
        val note: Note? = requireArguments().getParcelable(ARG_NOTE)
        if (note != null) {
            title.text = note.title
        }
    }

    companion object {
        const val TAG = "NoteDetailsFragment"
        private const val ARG_NOTE = "ARG_NOTE"
        fun newInstance(note: Note?): NoteDetailsFragment {
            val fragment = NoteDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_NOTE, note)
            fragment.arguments = args
            return fragment
        }
    }
}