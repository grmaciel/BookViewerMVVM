package com.gilson.bookviewer.data.book.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "sections")
data class SectionRoom(@PrimaryKey
                       val name: String,
                       @ColumnInfo
                       val createdAt: Long)