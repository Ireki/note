package com.example.note.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1)
@TypeConverters(Converters::class)
abstract class NoteRoomDatabase :RoomDatabase(){

    abstract fun noteDao(): NoteDao

    companion object{
        private const val databaseName = "note_database"
        fun buildDatabase(context: Context): NoteRoomDatabase {
            return Room.databaseBuilder(context, NoteRoomDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}