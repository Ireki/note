package com.example.note.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query("select * from notes_table where noteId = :noteId")
    fun getNote(noteId: Int): Flow<Note>

    @Query("select * from notes_table order by noteId desc")
    fun getAllNotes(): Flow<List<Note>>

    @Delete
    suspend fun delete(note: Note)
}