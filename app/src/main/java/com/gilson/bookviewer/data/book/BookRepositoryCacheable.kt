package com.gilson.bookviewer.data.book

import com.gilson.bookviewer.domain.book.BookRepository
import com.gilson.bookviewer.domain.book.BookWeekly
import io.reactivex.Observable

interface BookRepositoryCacheable : BookRepository {
    fun cacheData(bookEvent: List<BookWeekly>): Observable<List<BookWeekly>>

    fun lastUpdatedData(): Observable<Long>
}