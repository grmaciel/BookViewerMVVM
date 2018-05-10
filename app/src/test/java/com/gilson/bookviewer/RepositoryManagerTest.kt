package com.gilson.bookviewer

import com.gilson.bookviewer.data.book.BookRepositoryCacheable
import com.gilson.bookviewer.data.book.BookRepositoryManager
import com.gilson.bookviewer.domain.book.Book
import com.gilson.bookviewer.domain.book.BookRepository
import com.gilson.bookviewer.domain.book.BookWeekly
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class RepositoryManagerTest {
    lateinit var manager: BookRepositoryManager
    lateinit var remoteRepo: BookRepository
    lateinit var localRepo: BookRepositoryCacheable

    @Before
    fun startUp() {
        remoteRepo = Mockito.mock(BookRepository::class.java)
        localRepo = Mockito.mock(BookRepositoryCacheable::class.java)
        manager = BookRepositoryManager(remoteRepo, localRepo)
    }

    @Test
    fun shouldQueryRemoteDataIfTheresNoLocalDataAndCacheIt() {
        val bookEvent = mockBooks()
        Mockito.`when`(localRepo.retrieveBooks()).thenReturn(Observable.empty())
        Mockito.`when`(localRepo.cacheData(bookEvent)).thenReturn(Observable.just(bookEvent))
        Mockito.`when`(localRepo.lastUpdatedData()).thenReturn(Observable.just(0L))

        Mockito.`when`(remoteRepo.retrieveBooks())
                .thenReturn(Observable
                        .just(bookEvent))
        val test = manager.retrieveBooks().test()
        test.awaitCount(1)
        test.assertNoErrors()
        test.assertValue(bookEvent)
        Mockito.verify(remoteRepo, Mockito.times(1)).retrieveBooks()
        Mockito.verify(localRepo, Mockito.times(1)).cacheData(bookEvent)
    }

    @Test
    fun shouldNotQueryRemoteWhenTheresNoUpdate() {
        val bookEvent = mockBooks()
        Mockito.`when`(localRepo.retrieveBooks()).thenReturn(Observable.just(bookEvent))
        Mockito.`when`(localRepo.cacheData(bookEvent)).thenReturn(Observable.just(bookEvent))
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.YEAR, 1)
        Mockito.`when`(localRepo.lastUpdatedData()).thenReturn(Observable.just(calendar.time.time))

        Mockito.`when`(remoteRepo.retrieveBooks())
                .thenReturn(Observable
                        .just(bookEvent))
        val test = manager.retrieveBooks().test()
        test.awaitCount(1)
        test.assertNoErrors()
        test.assertValue(bookEvent)
        Mockito.verify(remoteRepo, Mockito.times(0)).retrieveBooks()
    }

    private fun mockBooks() = listOf(BookWeekly("W1", listOf(Book("T", "T"))))
}