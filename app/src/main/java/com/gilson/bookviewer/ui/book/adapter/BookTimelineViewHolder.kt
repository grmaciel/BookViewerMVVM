package com.gilson.bookviewer.ui.book.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.gilson.bookviewer.ui.book.BookTimeline

abstract class BookTimelineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun render(input: BookTimeline)
}