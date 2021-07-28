package com.android.FinalNotes.ui

import androidx.fragment.app.FragmentManager
import com.android.FinalNotes.R
import com.android.FinalNotes.domain.Note
import com.android.FinalNotes.ui.auth.AuthFragment
import com.android.FinalNotes.ui.info.InfoFragment
import com.android.FinalNotes.ui.notes.NotesFragment
import com.android.FinalNotes.ui.update.UpdateNoteFragment
import com.android.FinalNotes.ui.update.UpdateNoteFragment.Companion.newInstance

class MainRouter(private val fragmentManager: FragmentManager) {
    fun showNotes() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, NotesFragment.newInstance(), NotesFragment.TAG)
                .commit()
    }

    fun showAuth() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, AuthFragment.newInstance(), AuthFragment.TAG)
                .commit()
    }

    fun showInfo() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, InfoFragment.newInstance(), InfoFragment.TAG)
                .commit()
    }

    fun showNoteDetail(note: Note?) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, NoteDetailsFragment.newInstance(note), NoteDetailsFragment.TAG)
                .addToBackStack(NoteDetailsFragment.TAG)
                .commit()
    }

    fun showEditNote(note: Note?) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, newInstance(note), UpdateNoteFragment.TAG)
                .addToBackStack(UpdateNoteFragment.TAG)
                .commit()
    }

    fun back() {
        fragmentManager.popBackStack()
    }
}