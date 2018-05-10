package com.gilson.bookviewer.ui.book.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.gilson.bookviewer.ui.book.BookTimeline

class BookViewAdapter(var data: MutableList<BookTimeline>) : RecyclerView.Adapter<BookTimelineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BookTimeline.viewHolder(viewType, parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BookTimelineViewHolder, position: Int) {
        holder.render(data[position])
    }

    override fun getItemViewType(position: Int) = data[position].adapterType()

    fun addBooks(books: List<BookTimeline>) {
        data.clear()
        data.addAll(books)
        notifyDataSetChanged()
    }

    fun clear() {
        data = mutableListOf()
        notifyDataSetChanged()
    }
}
