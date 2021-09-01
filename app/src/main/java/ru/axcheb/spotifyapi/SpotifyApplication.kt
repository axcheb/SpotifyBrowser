package ru.axcheb.spotifyapi

import android.app.Application
import android.content.Context
import ru.axcheb.spotifyapi.di.AppComponent
import ru.axcheb.spotifyapi.di.DaggerAppComponent
import timber.log.Timber

class SpotifyApplication : Application() {

    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate() {
        super.onCreate()

        _appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is SpotifyApplication -> appComponent
        else -> this.applicationContext.appComponent
    }