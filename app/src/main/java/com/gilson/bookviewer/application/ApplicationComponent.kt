package com.gilson.bookviewer.application

import android.arch.lifecycle.ViewModelProvider
import com.gilson.bookviewer.di.RepositoryModule
import com.gilson.bookviewer.di.UseCaseModule
import com.gilson.bookviewer.di.ViewMapperModule
import com.gilson.bookviewer.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    ApplicationModule::class,
    RepositoryModule::class,
    UseCaseModule::class,
    ViewModelModule::class,
    ViewMapperModule::class
])
interface ApplicationComponent {
    fun inject(app: BookViewerApplication)

    fun provideVMFactory(): ViewModelProvider.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: BookViewerApplication): Builder

        fun build(): ApplicationComponent
    }
}