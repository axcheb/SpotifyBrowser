package ru.axcheb.spotifyapi.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.axcheb.spotifyapi.ui.album.AlbumFragment
import ru.axcheb.spotifyapi.ui.artist.ArtistFragment
import ru.axcheb.spotifyapi.ui.auth.AuthFragment
import ru.axcheb.spotifyapi.ui.main.MainActivity
import ru.axcheb.spotifyapi.ui.main.MainFragment
import ru.axcheb.spotifyapi.ui.playlist.PlaylistFragment
import ru.axcheb.spotifyapi.ui.playlists.PlaylistsFragment
import ru.axcheb.spotifyapi.ui.search.SearchFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(authFragment: AuthFragment)
    fun inject(playlistsFragment: PlaylistsFragment)
    fun inject(playlistFragment: PlaylistFragment)
    fun inject(albumFragment: AlbumFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(artistFragment: ArtistFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}
