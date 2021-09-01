package ru.axcheb.spotifyapi.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.axcheb.spotifyapi.R
import ru.axcheb.spotifyapi.appComponent

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

}