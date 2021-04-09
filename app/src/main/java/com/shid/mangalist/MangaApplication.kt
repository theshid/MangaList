package com.shid.mangalist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MangaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}