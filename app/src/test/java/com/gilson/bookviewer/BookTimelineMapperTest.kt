package com.gilson.bookviewer

import com.gilson.bookviewer.domain.book.Book
import com.gilson.bookviewer.domain.book.BookWeekly
import com.gilson.bookviewer.ui.book.BookTimeline
import com.gilson.bookviewer.ui.book.BookTimelineMapper
import com.gilson.bookviewer.ui.book.BookTimelineType
import io.reactivex.Observable
import org.junit.Test

class BookTimelineMapperTest {
    val mapper = BookTimelineMapper()
    @Test
    fun shouldParseBookEventEntity() {
        val result = Observable.just(listOf(BookWeekly("W1", listOf(Book("", "")))))
                .map(mapper)
                .test()
        result.awaitTerminalEvent()
        result.assertValue(listOf(BookTimeline(section = "W1", type = BookTimelineType.HEADER),
                BookTimeline("", "", null, BookTimelineType.BOOK)))
    }
}