package com.gilson.bookviewer.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gilson.bookviewer.R
import com.gilson.bookviewer.ui.book.adapter.BookSectionViewHolder
import com.gilson.bookviewer.ui.book.adapter.BookTimelineViewHolder
import com.gilson.bookviewer.ui.book.adapter.BookViewHolder

data class BookTimeline(val author: String? = null,
                        val title: String? = null,
                        val section: String? = null,
                        val type: BookTimelineType) {
    fun adapterType() = type.ordinal

    companion object {
        fun viewHolder(viewType: Int, parent: ViewGroup): BookTimelineViewHolder {
            return when (viewType) {
                BookTimelineType.HEADER.ordinal -> BookSectionViewHolder(
                        LayoutInflater
                                .from(parent.context)
                                .inflate(R.layout.adapter_book_section_header, parent, false))
                BookTimelineType.BOOK.ordinal -> BookViewHolder(LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.adapter_book_item, parent, false))
                else -> throw IllegalArgumentException()
            }
        }
    }
}