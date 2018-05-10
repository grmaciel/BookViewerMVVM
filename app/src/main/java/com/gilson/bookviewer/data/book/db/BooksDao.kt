package com.gilson.bookviewer.data.book.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface BooksDao {
    @Query("SELECT * from books")
    fun books(): Single<List<BookRoom>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertBatch(books: List<BookRoom>)
}