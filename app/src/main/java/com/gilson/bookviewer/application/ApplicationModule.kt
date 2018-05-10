package com.gilson.bookviewer.application

import android.arch.persistence.room.Room
import android.content.Context
import com.gilson.bookviewer.data.book.db.BooksDatabase
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun provideApplicationContext(application: BookViewerApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun provideAppDB(application: BookViewerApplication): BooksDatabase {
        return Room.databaseBuilder(application, BooksDatabase::class.java, "books-database").build()
    }
}