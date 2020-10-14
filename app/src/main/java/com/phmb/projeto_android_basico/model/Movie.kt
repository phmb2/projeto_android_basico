package com.phmb.projeto_android_basico.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(val title: String, val releaseDate: String, val direction: String, var image: Int): Parcelable