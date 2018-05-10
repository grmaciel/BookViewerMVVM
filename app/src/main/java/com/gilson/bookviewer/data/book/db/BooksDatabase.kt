package com.gilson.bookviewer.data.book.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [BookRoom::class, SectionRoom::class], version = 1, exportSchema = false)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao

    abstract fun sectionsBooksDao(): SectionsDao
}