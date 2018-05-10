package com.gilson.bookviewer.data.book

import android.arch.persistence.room.EmptyResultSetException
import android.database.sqlite.SQLiteConstraintException
import com.gilson.bookviewer.data.book.db.BookRoom
import com.gilson.bookviewer.data.book.db.SectionRoom
import com.gilson.bookviewer.data.book.db.SectionsDao
import com.gilson.bookviewer.domain.book.Book
import com.gilson.bookviewer.domain.book.BookWeekly
import com.gilson.bookviewer.data.book.db.BooksDao
import io.reactivex.Observable
import java.util.Date

class BookLocalRepository(private val dao: BooksDao,
                          private val sectionDao: SectionsDao) : BookRepositoryCacheable {
    override fun lastUpdatedData(): Observable<Long> {
        return sectionDao.lastCreatedDate()
                .toObservable()
                .onErrorResumeNext(handleEmptyResultError())
    }

    private fun handleEmptyResultError() =
            { error: Throwable -> if (error is EmptyResultSetException) Observable.just(0L) else Observable.error(error) }

    override fun cacheData(books: List<BookWeekly>): Observable<List<BookWeekly>> {
        return Observable.fromIterable(books)
                .flatMap { section -> insertSectionsIntoDb(section) }
                .flatMap { section -> retrieveBooksFromSection(section) }
                .map { mapBooksIntoDbObject(it) }
                .toList()
                .flatMapObservable { insertBooksIntoDb(it) }
                .flatMap { retrieveBooks() }
                .onErrorResumeNext { error: Throwable -> handleMultipleInsertWhenRemoteRepeatItself(error) }
    }

    private fun insertSectionsIntoDb(section: BookWeekly) =
            Observable.fromCallable { sectionDao.insertSection(SectionRoom(section.week, Date().time)) }
                    .map { section }

    private fun insertBooksIntoDb(it: List<BookRoom>) =
            Observable.fromCallable { dao.insertBatch(it) }

    private fun mapBooksIntoDbObject(it: Pair<String, Book>): BookRoom {
        val book = it.second
        val id = it.first
        return BookRoom(book.title, book.author, id)
    }

    private fun retrieveBooksFromSection(section: BookWeekly) =
            Observable.fromIterable(section.books).map { Pair(section.week, it) }

    private fun handleMultipleInsertWhenRemoteRepeatItself(error: Throwable) =
            if (error is SQLiteConstraintException) Observable.empty<List<BookWeekly>>() else Observable.error(error)

    override fun retrieveBooks(): Observable<List<BookWeekly>> {
        return sectionDao.sectionsWithBooks()
                .flatMapObservable { Observable.fromIterable(it) }
                .map { BookWeekly(it.section!!.name, it.books!!.map { Book(it.author, it.title) }) }
                .toList()
                .toObservable()
    }
}