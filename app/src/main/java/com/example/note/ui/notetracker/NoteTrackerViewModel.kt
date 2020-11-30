package com.example.note.ui.notetracker

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.note.data.Note
import com.example.note.repo.NoteRepository

class NoteTrackerViewModel @ViewModelInject constructor(
    repository: NoteRepository) : ViewModel() {

    val notes: LiveData<List<Note>> = repository.noteList.asLiveData()

    private val _navigateNoteContent = MutableLiveData<Int>()
    val navigateNoteContent
        get() = _navigateNoteContent

    fun onNoteClicked(id: Int) {
        _navigateNoteContent.value = id
    }

    fun onNoteContentNavigated() {
        _navigateNoteContent.value = null
    }

    fun addNoteContentNavigated() {
        _navigateNoteContent.value = -1
    }

}


