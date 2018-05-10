package com.gilson.bookviewer.ui.book.adapter

import android.view.View
import com.gilson.bookviewer.ui.book.BookTimeline
import kotlinx.android.synthetic.main.adapter_book_item.view.*

class BookViewHolder(view: View) : BookTimelineViewHolder(view) {
    override fun render(input: BookTimeline) {
        itemView.txtBookAuthor.text = input.author
        itemView.txtBookTitle.text = input.title
    }
}