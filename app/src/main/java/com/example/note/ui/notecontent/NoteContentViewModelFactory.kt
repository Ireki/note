package com.example.note.ui.notecontent

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note.data.Note
import com.example.note.repo.NoteRepository

class NoteContentViewModelFactory(
    private val repo: NoteRepository,
    private val noteId: Int = -1) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteContentViewModel::class.java)) {
            return NoteContentViewModel(repo, noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}