package com.example.note.ui.notecontent

import androidx.lifecycle.*
import com.example.note.data.Note
import com.example.note.repo.NoteRepository
import kotlinx.coroutines.launch


class NoteContentViewModel(
    private val repo: NoteRepository,
    private val noteId: Int = -1
) : ViewModel() {

    var  noteBeingModified: Note = emptyNote

    private var isEdit: Boolean = false

    init {
        viewModelScope.launch {
            if (noteId != -1) {
                noteBeingModified = repo.getNote(noteId)
                isEdit = true
            }
        }
    }

    private fun insert(note: Note) {
        repo.insert(note)
    }

    private fun update(note: Note) {
        repo.update(note)
    }

    fun saveNote() {
        if (!isEdit) {
            insert(noteBeingModified)
        } else {
            update(noteBeingModified)
        }
    }


    private val emptyNote: Note
        get() {
            return Note(noteText = "")
        }
}