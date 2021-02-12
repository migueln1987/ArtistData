package com.random.artistdata.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrackDTO(
    var trackId: Int?,
    var artistName: String?,
    var trackName: String?,
    var trackPrice: String?,
    var releaseDate: String?,
    var primaryGenreName: String?
) : Parcelable
