package com.gilson.bookviewer.data.book.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "books",
        foreignKeys = [ForeignKey(entity = SectionRoom::class,
                parentColumns = ["name"],
                childColumns = ["sectionName"],
                onDelete = ForeignKey.CASCADE)])
data class BookRoom(
        @PrimaryKey
        @ColumnInfo
        val title: String,
        @ColumnInfo
        val author: String,
        @ColumnInfo
        val sectionName: String)
