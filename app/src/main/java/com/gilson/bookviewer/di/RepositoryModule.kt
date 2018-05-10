package com.gilson.bookviewer.di

import com.gilson.bookviewer.data.book.BookLocalRepository
import com.gilson.bookviewer.data.book.BookRemoteRepository
import com.gilson.bookviewer.data.book.BookRepositoryCacheable
import com.gilson.bookviewer.data.book.BookRepositoryManager
import com.gilson.bookviewer.data.book.db.BooksDatabase
import com.gilson.bookviewer.domain.book.BookRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    @Named("remote")
    fun provideBooksRemoteRepository(): BookRepository {
        return BookRemoteRepository()
    }

    @Provides
    @Singleton
    fun provideBooksLocalRepository(db: BooksDatabase): BookRepositoryCacheable {
        return BookLocalRepository(db.booksDao(), db.sectionsBooksDao())
    }

    @Provides
    @Singleton
    fun provideBooksRepository(@Named("remote") remoteRepository: BookRepository, localRepository: BookRepositoryCacheable): BookRepository {
        return BookRepositoryManager(remoteRepository, localRepository)
    }
}