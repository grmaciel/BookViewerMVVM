package com.gilson.bookviewer.di

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    @ForActivity
    fun provideContext(): Context {
        return activity
    }

    @Provides
    @ForActivity
    fun provideFragmentManager(): FragmentManager {
        return activity.supportFragmentManager
    }
}