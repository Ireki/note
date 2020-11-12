package com.example.note.ui.notetracker

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.Note
import com.example.note.data.NoteRoomDatabase
import com.example.note.repo.NoteRepository
import com.example.note.util.provideRepository

class NoteTrackerViewModel(
    application: Application) : ViewModel() {

    val repository: NoteRepository = provideRepository(application.applicationContext)
    val notes: LiveData<List<Note>>

    init{
        notes = repository.noteList
    }

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


