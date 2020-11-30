package com.example.note.repo

import androidx.lifecycle.LiveData
import com.example.note.data.Note
import com.example.note.data.NoteRoomDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteDatabase: NoteRoomDatabase) {

    val noteList: Flow<List<Note>> get() = noteDatabase.noteDao().getAllNotes()

    fun getNote(noteId: Int): Flow<Note> = noteDatabase.noteDao().getNote(noteId)

    suspend fun insert(note: Note) {
        noteDatabase.noteDao().insert(note)
    }

    suspend fun update(note: Note) {
        noteDatabase.noteDao().update(note)
    }

    suspend fun delete(note: Note) {
        noteDatabase.noteDao().delete(note)
    }


    companion object {
        private var sInstance: NoteRepository? = null
        fun getInstance(database: NoteRoomDatabase): NoteRepository {
            return sInstance ?: synchronized(this) {
                sInstance
                    ?: NoteRepository(
                        database,
                    ).also { sInstance = it }
            }
        }
    }
}