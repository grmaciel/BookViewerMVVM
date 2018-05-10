package com.gilson.bookviewer.domain.book

import io.reactivex.Observable

interface BookRepository {
    fun retrieveBooks(): Observable<List<BookWeekly>>
}