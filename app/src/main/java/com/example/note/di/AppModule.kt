package com.example.note.di

import android.content.Context
import com.example.note.data.NoteRoomDatabase
import com.example.note.repo.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteRoomDatabase {
        return NoteRoomDatabase.buildDatabase(context)
    }

    @Singleton
    @Provides
    fun provideAgendaRepository(database: NoteRoomDatabase): NoteRepository =
        NoteRepository.getInstance(database)

}