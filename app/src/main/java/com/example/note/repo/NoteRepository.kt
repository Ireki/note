package com.example.note.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.enpassion.twowaydatabindingkotlin.utils.AppExecutors
import com.example.note.data.Note
import com.example.note.data.NoteDao
import com.example.note.data.NoteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class NoteRepository (private val noteDatabase: NoteRoomDatabase, private val mExecutors: AppExecutors) {

    val noteList: LiveData<List<Note>>
        get() = noteDatabase.noteDao().getAllNotes()

    suspend fun getNote(noteId: Int): Note {
        return noteDatabase.noteDao().getNote(noteId)
    }

    fun insert(note: Note) {
        mExecutors.diskIO().execute{ noteDatabase.noteDao().insert(note) }
    }

    fun update(note: Note) {
        mExecutors.diskIO().execute{ noteDatabase.noteDao().update(note) }
    }

    fun delete(note: Note) {
        mExecutors.diskIO().execute{ noteDatabase.noteDao().delete(note) }
    }

    companion object {

        private var sInstance: NoteRepository? = null

        fun getInstance(database: NoteRoomDatabase, executors: AppExecutors): NoteRepository {
            return sInstance ?: synchronized(this) {
                sInstance
                    ?: NoteRepository(
                        database,
                        executors
                    ).also { sInstance = it }
            }
        }
    }
}