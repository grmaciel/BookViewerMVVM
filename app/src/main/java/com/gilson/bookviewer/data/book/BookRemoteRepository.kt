package com.gilson.bookviewer.data.book

import com.gilson.bookviewer.domain.book.Book
import com.gilson.bookviewer.domain.book.BookRepository
import com.gilson.bookviewer.domain.book.BookWeekly
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class BookRemoteRepository : BookRepository {
    override fun retrieveBooks(): Observable<List<BookWeekly>> {
        return Observable.timer(2, TimeUnit.SECONDS)
                .map { listOf(mockData()) }
    }

    private fun mockData(): BookWeekly {
        return if ((Random().nextInt(10) % 2) == 0) {
            oneOfTheMockedDate()
        } else {
            otherMockedData()
        }
    }

    private fun oneOfTheMockedDate() = BookWeekly("Week that these books belong", listOf(Book("Gilson Maciel", "Finding an apartment in Berlin"),
            Book("O RLY", "Googling the error Message"),
            Book("Unknown", "Code challenges: A race against time"),
            Book("Random Programmer", "Works on my device")))

    private fun otherMockedData() = BookWeekly("Week that these other books belong", listOf(Book("Author1", "This is the new week thing1"),
            Book("Author2", "This is the new week thing2"),
            Book("Author3", "This is the new week thing3"),
            Book("Author4", "This is the new week thing4")))
}