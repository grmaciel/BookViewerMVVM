package com.gilson.bookviewer.domain.book

import io.reactivex.Observable

class BookUseCase(val repository: BookRepository) {

    fun retrieveBooks(): Observable<List<BookWeekly>> {
        return repository.retrieveBooks()
    }
}