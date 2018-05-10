package com.gilson.bookviewer.ui.book.adapter

import android.view.View
import com.gilson.bookviewer.ui.book.BookTimeline
import kotlinx.android.synthetic.main.adapter_book_section_header.view.*

class BookSectionViewHolder(view: View) : BookTimelineViewHolder(view) {
    override fun render(input: BookTimeline) {
        itemView.txtAdapterHeader.text = input.section
    }
}