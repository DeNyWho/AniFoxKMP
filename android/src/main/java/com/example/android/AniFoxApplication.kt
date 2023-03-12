package com.example.android

import android.app.Application
import com.example.android.di.appModule
import com.example.common.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.osmdroid.config.Configuration
import java.io.File


class AniFoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        Configuration.getInstance().osmdroidTileCache = File(cacheDir, "osm").also {
            it.mkdir()
        }


        initKoin {
            androidLogger()
            androidContext(this@AniFoxApplication)
            modules(appModule)
        }
    }
}
