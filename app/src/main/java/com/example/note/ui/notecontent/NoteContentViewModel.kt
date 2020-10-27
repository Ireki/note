package com.example.note.ui.notecontent

import androidx.lifecycle.*
import com.example.note.data.Note
import com.example.note.repo.NoteRepository
import kotlinx.coroutines.launch


class NoteContentViewModel(
    private val repo: NoteRepository,
    private val noteId: Int = -1
) : ViewModel() {


    var noteModified = MutableLiveData<Note>(Note(noteText = ""))

    private var isEdit: Boolean = false

    init {
        viewModelScope.launch {
            if (noteId != -1) {
                //noteModified = repo.getNote(noteId) as MutableLiveData<Note>
                isEdit = true
            }
        }
    }

    private fun insert(note: Note?) {
        note?.let{
            repo.insert(it)
        }

    }

    private fun update(note: Note?) {
        note?.let{
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