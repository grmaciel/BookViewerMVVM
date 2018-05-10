package com.gilson.bookviewer.data.book.db

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class SectionsAndBooks {
    @Embedded
    var section: SectionRoom? = null

    @Relation(parentColumn = "name", entityColumn = "sectionName", entity = BookRoom::class)
    var books: List<BookRoom>? = null
}