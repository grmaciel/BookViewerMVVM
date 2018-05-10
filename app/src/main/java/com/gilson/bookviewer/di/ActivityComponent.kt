package com.gilson.bookviewer.di

import com.gilson.bookviewer.application.ApplicationComponent
import com.gilson.bookviewer.ui.book.BookActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class],
        modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: BookActivity)
}