package com.gilson.bookviewer.data.book.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface SectionsDao {
    @Query("SELECT * from sections order by createdAt desc")
    fun sectionsWithBooks(): Single<List<SectionsAndBooks>>

    @Query("SELECT createdAt from sections order by createdAt asc limit 1")
    fun lastCreatedDate(): Single<Long>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertSection(sectionRoom: SectionRoom)
}