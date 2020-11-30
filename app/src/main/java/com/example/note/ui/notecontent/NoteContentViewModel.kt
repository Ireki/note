package com.example.note.ui.notecontent

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.note.data.Note
import com.example.note.repo.NoteRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*


class NoteContentViewModel @ViewModelInject constructor(
    private val repo: NoteRepository,
) : ViewModel() {

    lateinit var noteModified: LiveData<Note>
    private var isEdit: Boolean = false

    fun setNote(noteId: Int){
        if (noteId != -1) {
            noteModified = repo.getNote(noteId).asLiveData()
            isEdit = true
        }
        else{
            noteModified = MutableLiveData<Note>(Note(noteText = ""))
        }
    }


    private fun insert(note: Note?) {
        viewModelScope.launch {
            note?.let{
                it.dateUpdate = Calendar.getInstance().getTime()
                repo.insert(it)
            }
        }
    }

    private fun update(note: Note?) {
         viewModelScope.launch {
             note?.let{
                 it.dateUpdate = Calendar.getInstance().getTime()
                 repo.update(it)
             }
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