package com.android.FinalNotes.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.FinalNotes.R
import com.android.FinalNotes.domain.Note
import com.android.FinalNotes.ui.notes.NotesAdapter.NoteViewHolder
import com.bumptech.glide.Glide
import java.util.*

class NotesAdapter(private val fragment: Fragment) : RecyclerView.Adapter<NoteViewHolder>() {
    private val notes = ArrayList<Note>()
    var listener: OnNoteClickedListener? = null
    var longClickedListener: OnNoteLongClickedListener? = null
    fun setData(toSet: List<Note>?) {
        notes.clear()
        notes.addAll(toSet!!)
    }

    fun add(addedNote: Note): Int {
        notes.add(addedNote)
        return notes.size - 1
    }

    fun remove(longClickedNote: Note) {
        notes.remove(longClickedNote)
    }

    fun update(note: Note) {
        for (i in notes.indices) {
            val item = notes[i]
            if (item.id == note.id) {
                notes.removeAt(i)
                notes.add(i, note)
                return
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title
        Glide.with(holder.image)
                .load(note.url)
                .centerCrop()
                .into(holder.image)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    interface OnNoteClickedListener {
        fun onNoteClickedListener(note: Note)
    }

    interface OnNoteLongClickedListener {
        fun onNoteLongClickedListener(note: Note, index: Int)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var image: ImageView

        init {
            fragment.registerForContextMenu(itemView)
            itemView.setOnClickListener {
                if (listener != null) {
                    listener!!.onNoteClickedListener(notes[adapterPosition])
                }
            }
            itemView.setOnLongClickListener {
                itemView.showContextMenu()
                if (longClickedListener != null) {
                    val index = adapterPosition
                    longClickedListener!!.onNoteLongClickedListener(notes[index], index)
                }
                true
            }
            title = itemView.findViewById(R.id.title)
            image = itemView.findViewById(R.id.image)
        }
    }
}