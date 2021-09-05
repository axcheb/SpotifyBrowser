package ru.axcheb.spotifyapi.ui

import android.graphics.Color
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

internal fun ImageView.circularPlaceholder(): CircularProgressDrawable =
    CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        setColorSchemeColors(Color.GRAY)
        start()
    }
