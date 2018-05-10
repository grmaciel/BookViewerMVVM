package com.gilson.bookviewer.application

import android.app.Application

class BookViewerApplication : Application() {
    var component: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        component().inject(this)
    }

    fun component(): ApplicationComponent {
        if (component == null) {
            component = DaggerApplicationComponent
                    .builder()
                    .application(this)
                    .build()
        }
        return component!!
    }
}