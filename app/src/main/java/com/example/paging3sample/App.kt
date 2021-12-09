package com.example.paging3sample

import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@GlideModule
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}