package com.android.FinalNotes.ui.notes

import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.android.FinalNotes.R
import com.android.FinalNotes.RouterHolder
import com.android.FinalNotes.domain.Callback
import com.android.FinalNotes.domain.Note
import com.android.FinalNotes.domain.NotesFirestoreRepository
import com.android.FinalNotes.ui.notes.NotesAdapter.OnNoteClickedListener
import com.android.FinalNotes.ui.notes.NotesAdapter.OnNoteLongClickedListener
import com.android.FinalNotes.ui.update.UpdateNoteFragment

class NotesFragment : Fragment() {
    private val repository = NotesFirestoreRepository.INSTANCE
    private var notesAdapter: NotesAdapter? = null
    private var longClickedIndex = 0
    private var longClickedNote: Note? = null
    private var isLoading = false
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesAdapter = NotesAdapter(this)
        isLoading = true
        with(repository) {
            getNotes(callback = object : Callback<List<Note?>?> {
                override fun onSuccess(result: List<Note?>?) {
                    fun onSuccess(result: List<Note?>?) {
                        notesAdapter!!.setData(result as List<Note>?)
                        notesAdapter!!.notifyDataSetChanged()
                        isLoading = false
                        if (progressBar != null) {
                            progressBar!!.visibility = View.GONE
                        }
                    }

                    fun onSuccess() {
                        TODO("Not yet implemented")
                    }
                }
            })
        }
        notesAdapter!!.listener = object : OnNoteClickedListener {
            override fun onNoteClickedListener(note: Note) {
                if (requireActivity() is RouterHolder) {
                    val router = (requireActivity() as RouterHolder).mainRouter
                    router!!.showNoteDetail(note)
                }
                //                Snackbar.make(view, note.getTitle(), Snackbar.LENGTH_SHORT).show();
            }
        }
        notesAdapter!!.longClickedListener = object : OnNoteLongClickedListener {
            override fun onNoteLongClickedListener(note: Note, index: Int) {
                longClickedIndex = index
                longClickedNote = note
            }
        }
        parentFragmentManager.setFragmentResultListener(UpdateNoteFragment.UPDATE_RESULT, this, { requestKey, result ->
            if (result.containsKey(UpdateNoteFragment.ARG_NOTE)) {
                val note: Note? = result.getParcelable(UpdateNoteFragment.ARG_NOTE)
                if (note != null) {
                    notesAdapter!!.update(note)
                }
                notesAdapter!!.notifyItemChanged(longClickedIndex)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val notesList: RecyclerView = view.findViewById(R.id.notes_list)
        val animator = DefaultItemAnimator()
        animator.addDuration = 5000L
        animator.removeDuration = 7000L
        notesList.itemAnimator = animator
        progressBar = view.findViewById(R.id.progress)
        if (isLoading) {
            with(progressBar) { this?.setVisibility(View.VISIBLE) }
        }
        toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_add) {
                repository.add("This is new added title", "https://cdn.pixabay.com/photo/2020/04/17/16/48/marguerite-5056063_1280.jpg", object : Callback<Note?> {
                    override fun onSuccess(result: Note?) {
                        val index = result?.let { notesAdapter!!.add(it) }
                        if (index != null) {
                            notesAdapter!!.notifyItemInserted(index)
                        }
                        if (index != null) {
                            notesList.scrollToPosition(index)
                        }
                    }
                })
                return@OnMenuItemClickListener true
            }
            if (item.itemId == R.id.action_clear) {
                repository.clear()
                notesAdapter!!.setData(emptyList())
                notesAdapter!!.notifyDataSetChanged()
                return@OnMenuItemClickListener true
            }
            false
        })
        val linearLayoutManager = LinearLayoutManager(requireContext())
        notesList.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_separator)!!)
        notesList.addItemDecoration(dividerItemDecoration)

        notesList.layoutManager = GridLayoutManager(requireContext(), 2);
        notesList.adapter = notesAdapter

//        for (Note note : notes) {
//
//            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, containerView, false);
//
//            TextView title = itemView.findViewById(R.id.title);
//            ImageView image = itemView.findViewById(R.id.image);
//
//            title.setText(note.getTitle());
//
//            Glide.with(this)
//                    .load(note.getUrl())
//                    .centerCrop()
//                    .into(image);
//
//            containerView.addView(itemView);
//        }
    }

    //    @Override
    //    public void onResume() {
    //        super.onResume();
    //
    //        new Handler().postDelayed(new Runnable() {
    //            @Override
    //            public void run() {
    //                notesAdapter.setData(new ArrayList<>());
    //                notesAdapter.notifyDataSetChanged();
    //
    //            }
    //        }, 2000L);
    //
    //    }
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.menu_notes_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_update) {
            if (requireActivity() is RouterHolder) {
                val router = (requireActivity() as RouterHolder).mainRouter
                router!!.showEditNote(longClickedNote)
            }
            return true
        }
        if (item.itemId == R.id.action_delete) {
            repository.remove(longClickedNote, object : Callback<Any?> {
                override fun onSuccess(result: Any?) {
                    notesAdapter!!.remove(longClickedNote!!)
                    notesAdapter!!.notifyItemRemoved(longClickedIndex)
                }
            })
            return true
        }
        return super.onContextItemSelected(item)
    }

    companion object {
        const val TAG = "NotesFragment"
        fun newInstance(): NotesFragment {
            return NotesFragment()
        }
    }
}