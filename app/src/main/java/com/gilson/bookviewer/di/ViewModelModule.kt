package com.gilson.bookviewer.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.gilson.bookviewer.ui.ViewModelFactory
import com.gilson.bookviewer.ui.book.BookViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(BookViewModel::class)
    abstract fun bindBookViewModel(eventViewModel: BookViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}