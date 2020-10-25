package com.example.note.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note)

    @Query("select * from notes_table where noteId = :noteId")
    suspend fun getNote(noteId: Int): Note

    @Query("select * from notes_table order by noteId desc")
    fun getAllNotes(): LiveData<List<Note>>

    @Delete
    fun delete(note: Note)
}