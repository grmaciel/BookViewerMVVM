package com.gilson.bookviewer

import android.arch.persistence.room.EmptyResultSetException
import com.gilson.bookviewer.data.book.BookLocalRepository
import com.gilson.bookviewer.data.book.db.BookRoom
import com.gilson.bookviewer.data.book.db.SectionRoom
import com.gilson.bookviewer.data.book.db.SectionsAndBooks
import com.gilson.bookviewer.data.book.db.SectionsDao
import com.gilson.bookviewer.domain.book.Book
import com.gilson.bookviewer.domain.book.BookWeekly
import com.gilson.bookviewer.data.book.db.BooksDao
import io.reactivex.Single
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class BookLocalRepositoryTest {
    lateinit var local: BookLocalRepository
    lateinit var bookDao: BooksDao
    lateinit var sectionDao: SectionsDao

    @Before
    fun startUp() {
        bookDao = Mockito.mock(BooksDao::class.java)
        sectionDao = Mockito.mock(SectionsDao::class.java)
        local = BookLocalRepository(bookDao, sectionDao)
    }

    @Test
    fun cacheDataShouldInsertSectionsAndBooks() {
        val data = listOf(BookWeekly("W1", listOf(Book("Me", "and Myself"))))
        val roomData = listOf(SectionsAndBooks()
                .apply {
                    this.books = listOf(BookRoom("and Myself", "Me", "W1"))
                    this.section = SectionRoom("W1", Date().time)
                })
        Mockito.`when`(sectionDao.sectionsWithBooks())
                .thenReturn(Single.just(roomData))
        val observer = local.cacheData(data).test()
        observer.awaitTerminalEvent()
        val result = observer.values().first()
        MatcherAssert.assertThat(result.first().week, Is.`is`("W1"))
        MatcherAssert.assertThat(result.first().books, Is.`is`(data.first().books))
        Mockito.verify(sectionDao, Mockito.times(1))
                .insertSection(Mockito.any(SectionRoom::class.java)
                        ?: SectionRoom("", Date().time))
        Mockito.verify(bookDao, Mockito.times(1))
                .insertBatch(roomData.first().books!!)
    }

    @Test
    fun whenRetrievingLastDateShouldReturn0ForEmptySetException() {
        Mockito.`when`(sectionDao.lastCreatedDate()).thenReturn(Single.error(EmptyResultSetException("Test")))
        val observer = local.lastUpdatedData().test()
        observer.assertValue(0L)
    }
}