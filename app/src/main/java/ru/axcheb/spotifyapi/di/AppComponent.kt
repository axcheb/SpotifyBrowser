package ru.axcheb.spotifyapi.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.axcheb.spotifyapi.ui.auth.AuthFragment
import ru.axcheb.spotifyapi.ui.main.MainActivity
import ru.axcheb.spotifyapi.ui.main.MainFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(authFragment: AuthFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}
