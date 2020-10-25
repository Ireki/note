package com.example.note.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1)
//@TypeConverters(Converters::class)
abstract class NoteRoomDatabase :RoomDatabase(){

    abstract fun noteDao(): NoteDao

    companion object{

        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        fun getDatabase(
            context: Context) : NoteRoomDatabase{
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    NoteRoomDatabase::class.java, "note_database")
                    .build()
                    .also { INSTANCE = it }
            }
        }


    }
}