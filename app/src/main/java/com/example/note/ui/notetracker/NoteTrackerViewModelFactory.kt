package com.example.note.ui.notetracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note.data.NoteDao
import java.lang.IllegalArgumentException

class NoteTrackerViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(NoteTrackerViewModel::class.java)){
                return NoteTrackerViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}