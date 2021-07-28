package com.android.FinalNotes.domain

import java.util.*

interface NotesRepository {
    fun getNotes(callback: Callback<List<Note?>?>?)
    fun clear()
    fun add(title: String?, imageUrl: String?, callback: Callback<Note?>?)
    fun remove(note: Note?, callback: Callback<Any?>?)
    fun update(note: Note, title: String?, date: Date?): Note?
}