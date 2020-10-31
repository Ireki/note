package com.example.note.ui.notecontent

import androidx.lifecycle.*
import com.example.note.data.Note
import com.example.note.repo.NoteRepository
import kotlinx.coroutines.launch
import java.util.*


class NoteContentViewModel(
    private val repo: NoteRepository,
    private val noteId: Int = -1
) : ViewModel() {

    var noteModified: LiveData<Note>

    private var isEdit: Boolean = false

    init {
        if (noteId != -1) {
            noteModified = repo.getNote(noteId)
            isEdit = true
        }
        else{
            noteModified = MutableLiveData<Note>(Note(noteText = ""))
        }
    }

    private fun insert(note: Note?) {
        note?.let{
            it.dateUpdate = Calendar.getInstance().getTime()
            repo.insert(it)
        }
    }

    private fun update(note: Note?) {
        note?.let{
            it.dateUpdate = Calendar.getInstance().getTime()
            repo.update(it)
        }
    }

    fun saveNote() {
        if (!isEdit) {
            insert(noteModified.value)

        } else {
            update(noteModified.value)
        }
    }

}