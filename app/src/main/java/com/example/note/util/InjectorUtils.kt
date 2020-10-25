package com.example.note.util

import android.content.Context
import com.enpassion.twowaydatabindingkotlin.utils.AppExecutors
import com.example.note.data.NoteRoomDatabase
import com.example.note.repo.NoteRepository

fun provideRepository(context: Context): NoteRepository {
    val executors = AppExecutors.getInstance()
    val db = NoteRoomDatabase.getDatabase(context)
    return NoteRepository.getInstance(db, executors)
}