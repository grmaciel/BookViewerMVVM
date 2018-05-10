package com.gilson.bookviewer.data.book

import com.gilson.bookviewer.domain.book.BookRepository
import com.gilson.bookviewer.domain.book.BookWeekly
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import java.util.*
import java.util.concurrent.TimeUnit

class BookRepositoryManager(private val remoteRepository: BookRepository,
                            private val localRepository: BookRepositoryCacheable) : BookRepository {
    private val booksSubject = ReplaySubject.create<List<BookWeekly>>()


    override fun retrieveBooks(): Observable<List<BookWeekly>> {
        retrieveData()
        return booksSubject
    }

    private fun retrieveData() {
        localRepository.retrieveBooks()
                .subscribeOn(Schedulers.io())
                .doOnComplete(queryRemoteData())
                .subscribe({ booksSubject.onNext(it) },
                        { emitError(it) })
    }

    private fun emitError(it: Throwable) {
        if (booksSubject.hasObservers()) booksSubject.onError(it)
    }

    private fun queryRemoteData() = Action {
        localRepository.lastUpdatedData()
                .filter(shouldQueryRemoteData())
                .flatMap { remoteRepository.retrieveBooks() }
                .flatMap(cacheDataLocally())
                .subscribe({
                    booksSubject.onNext(it)
                }, {
                    emitError(it)
                })
    }

    private fun shouldQueryRemoteData() = Predicate<Long> {
        if (it > 0) {
            val date = Date(it)
            val elapsed = Date().time - date.time
            TimeUnit.MILLISECONDS.convert(elapsed, TimeUnit.MILLISECONDS) > 300
        } else {
            true
        }
    }

    private fun cacheDataLocally() = Function<List<BookWeekly>, Observable<List<BookWeekly>>> {
        localRepository.cacheData(it)
    }
}