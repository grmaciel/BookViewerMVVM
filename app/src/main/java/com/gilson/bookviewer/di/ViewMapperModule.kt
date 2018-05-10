package com.gilson.bookviewer.di

import com.gilson.bookviewer.domain.book.BookWeekly
import com.gilson.bookviewer.ui.book.BookTimeline
import com.gilson.bookviewer.ui.book.BookTimelineMapper
import dagger.Module
import dagger.Provides
import io.reactivex.functions.Function

@Module
class ViewMapperModule {
    @Provides
    fun providesBookMapper(): Function<List<BookWeekly>, List<BookTimeline>> {
        return BookTimelineMapper()
    }
}