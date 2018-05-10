package com.gilson.bookviewer.ui.book

import com.gilson.bookviewer.domain.book.BookWeekly
import io.reactivex.functions.Function

class BookTimelineMapper : Function<List<BookWeekly>, List<BookTimeline>> {
    override fun apply(input: List<BookWeekly>): List<BookTimeline> {
        val data = mutableListOf<BookTimeline>()
        input.forEach {
            data.add(BookTimeline(section = it.week, type = BookTimelineType.HEADER))
            data.addAll(it.books.map {
                BookTimeline(type = BookTimelineType.BOOK,
                        author = it.author,
                        title = it.title) })
        }
        return data
    }
}