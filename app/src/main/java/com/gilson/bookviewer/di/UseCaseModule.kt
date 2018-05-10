package com.gilson.bookviewer.di

import com.gilson.bookviewer.domain.book.BookRepository
import com.gilson.bookviewer.domain.book.BookUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideBookUseCase(repository: BookRepository): BookUseCase {
        return BookUseCase(repository)
    }
}