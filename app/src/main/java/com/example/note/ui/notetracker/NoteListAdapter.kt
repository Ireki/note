package com.example.note.ui.notetracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note.R
import com.example.note.data.Note
import com.example.note.databinding.ListItemNoteBinding


class NoteListAdapter(private val listener: NoteListener): RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    var noteList: List<Note>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder = NoteViewHolder.from(parent)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) = holder.bind(noteList?.get(position), listener)

    class NoteViewHolder(private val binding: ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentNote: Note?, clickListener: NoteListener){
            binding.note = currentNote
            binding.noteItemClick = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NoteViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ListItemNoteBinding>(
                    layoutInflater,
                    R.layout.list_item_note,
                    parent,
                    false
                )
                return NoteViewHolder(binding)
            }
        }
    }

    internal fun setNotes(notes: List<Note>) {
        this.noteList = notes
    }

    override fun getItemCount() = noteList?.size ?: 0

}


class NoteListener(val clickListener: (noteId: Int) -> Unit) {
    fun onClick(note: Note) = clickListener(note.noteId)
}