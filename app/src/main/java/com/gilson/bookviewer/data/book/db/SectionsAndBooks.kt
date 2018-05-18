package com.gilson.bookviewer.data.book.db

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class SectionsAndBooks {
    @Embedded
    lateinit var section: SectionRoom

    @Relation(parentColumn = "name", entityColumn = "sectionName", entity = BookRoom::class)
    lateinit var books: List<BookRoom>
}