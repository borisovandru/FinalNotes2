package com.android.FinalNotes.domain

import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import java.util.*

class NotesFirestoreRepository : NotesRepository {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    override fun getNotes(callback: Callback<List<Note?>?>?) {
        firebaseFirestore.collection(NOTES)
                .orderBy(DATE, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task: Task<QuerySnapshot?> ->
                    if (task.isSuccessful) {
                        val result = ArrayList<Note>()
                        for (document in Objects.requireNonNull(task.result)!!) {
                            val title = document[TITLE] as String?
                            val image = document[IMAGE] as String?
                            val date = (Objects.requireNonNull(document[DATE]) as Timestamp).toDate()
                            result.add(Note(document.id, title, image, date))
                        }
                        callback?.onSuccess(result)
                    } else {
                        task.exception
                    }
                }
    }

    override fun clear() {}
    override fun add(title: String?, imageUrl: String?, callback: Callback<Note?>?) {
        val data = HashMap<String, Any>()
        val date = Date()
        title.also { data[TITLE] = it!! }
        imageUrl.also { data[IMAGE] = it!! }
        data[DATE] = date
        firebaseFirestore.collection(NOTES)
                .add(data)
                .addOnCompleteListener { task: Task<DocumentReference?> ->
                    if (task.isSuccessful) {
                        val note = Note(Objects.requireNonNull(task.result)?.id, title, imageUrl, date)
                        callback?.onSuccess(note)
                    }
                }
    }

    override fun remove(note: Note?, callback: Callback<Any?>?) {
        if (note != null) {
            firebaseFirestore.collection(NOTES)
                    .document(note.id!!)
                    .delete()
                    .addOnCompleteListener { task: Task<Void?> ->
                        if (task.isSuccessful) {
                            callback?.onSuccess(note)
                        }
                    }
        }
    }

    override fun update(note: Note, title: String?, date: Date?): Note? {
        return null
    }

    companion object {
        @JvmField
        val INSTANCE: NotesRepository = NotesFirestoreRepository()
        private const val NOTES = "notes"
        private const val DATE = "date"
        private const val TITLE = "title"
        private const val IMAGE = "image"
    }
}