package com.android.FinalNotes.ui;

import androidx.fragment.app.FragmentManager;

import com.android.FinalNotes.R;
import com.android.FinalNotes.domain.Note;
import com.android.FinalNotes.ui.auth.AuthFragment;
import com.android.FinalNotes.ui.info.InfoFragment;
import com.android.FinalNotes.ui.notes.NotesFragment;
import com.android.FinalNotes.ui.update.UpdateNoteFragment;

public class MainRouter {

    private final FragmentManager fragmentManager;

    public MainRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotes() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, NotesFragment.newInstance(), NotesFragment.TAG)
                .commit();
    }

    public void showAuth() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, AuthFragment.newInstance(), AuthFragment.TAG)
                .commit();
    }

    public void showInfo() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, InfoFragment.newInstance(), InfoFragment.TAG)
                .commit();
    }

    public void showNoteDetail(Note note) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, NoteDetailsFragment.newInstance(note), NoteDetailsFragment.TAG)
                .addToBackStack(NoteDetailsFragment.TAG)
                .commit();
    }


    public void showEditNote(Note note) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, UpdateNoteFragment.newInstance(note), UpdateNoteFragment.TAG)
                .addToBackStack(UpdateNoteFragment.TAG)
                .commit();
    }

    public void back() {
        fragmentManager.popBackStack();
    }

}
