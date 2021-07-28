package com.android.FinalNotes.domain

import android.os.Handler
import android.os.Looper
import java.util.*
import java.util.concurrent.Executors

class NotesRepositoryImpl : NotesRepository {
    private val notes = ArrayList<Note>()
    private val executor = Executors.newCachedThreadPool()
    private val handler = Handler(Looper.getMainLooper())
    override fun getNotes(callback: Callback<List<Note?>?>?) {
        executor.execute {
            try {
                Thread.sleep(2000L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            handler.post {
                callback?.onSuccess(notes)
            }
        }
    }

    override fun clear() {
        notes.clear()
    }

    override fun add(title: String?, imageUrl: String?, callback: Callback<Note?>?) {
        val note = Note(UUID.randomUUID().toString(), title, imageUrl, Date())
        notes.add(note)
        callback?.onSuccess(note)
    }

    override fun remove(note: Note?, callback: Callback<Any?>?) {
        notes.remove(note)
        callback?.onSuccess(note)
    }

    override fun update(note: Note, title: String?, date: Date?): Note {
        for (i in notes.indices) {
            val item = notes[i]
            if (item.id == note.id) {
                var titleToSet = item.title
                var dateToSet: Date? = item.date
                if (title != null) {
                    titleToSet = title
                }
                if (date != null) {
                    dateToSet = date
                }
                val newNote = Note(note.id, titleToSet, note.url, dateToSet!!)
                notes.removeAt(i)
                notes.add(i, newNote)
                return newNote
            }
        }
        return note
    }

    companion object {
        val INSTANCE: NotesRepository = NotesRepositoryImpl()
    }

    init {
        notes.add(Note("id1", "Title Number One", "https://cdn.pixabay.com/photo/2020/12/25/11/57/flamingos-5859192_1280.jpg", Date()))
        notes.add(Note("id2", "Title Number Two", "https://cdn.pixabay.com/photo/2020/04/17/16/48/marguerite-5056063_1280.jpg", Date()))
        notes.add(Note("id3", "Title Number Three", "https://cdn.pixabay.com/photo/2020/07/06/01/33/sky-5375005_1280.jpg", Date()))
        notes.add(Note("id4", "Title Number Four", "https://cdn.pixabay.com/photo/2021/03/17/09/06/snowdrop-6101818_1280.jpg", Date()))
        notes.add(Note("id5", "Title Number Five", "https://cdn.pixabay.com/photo/2020/06/22/10/40/castle-5328719_1280.jpg", Date()))
        notes.add(Note("id6", "Title Number Six", "https://cdn.pixabay.com/photo/2020/12/25/11/57/flamingos-5859192_1280.jpg", Date()))
        notes.add(Note("id7", "Title Number Seven", "https://cdn.pixabay.com/photo/2020/07/06/01/33/sky-5375005_1280.jpg", Date()))
        notes.add(Note("id8", "Title Number Eight", "https://cdn.pixabay.com/photo/2020/06/22/10/40/castle-5328719_1280.jpg", Date()))
        notes.add(Note("id9", "Title Number Nine", "https://cdn.pixabay.com/photo/2021/03/17/09/06/snowdrop-6101818_1280.jpg", Date()))
        notes.add(Note("id10", "Title Number Ten", "https://cdn.pixabay.com/photo/2020/04/17/16/48/marguerite-5056063_1280.jpg", Date()))
        notes.add(Note("id11", "Title Number Eleven", "https://cdn.pixabay.com/photo/2020/12/25/11/57/flamingos-5859192_1280.jpg", Date()))
        notes.add(Note("id12", "Title Number Twelve", "https://cdn.pixabay.com/photo/2020/07/06/01/33/sky-5375005_1280.jpg", Date()))
    }
}