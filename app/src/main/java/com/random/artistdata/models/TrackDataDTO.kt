package com.random.artistdata.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrackDataDTO(
    var resultCount: Int?,
    var results: List<TrackDTO>?,
) : Parcelable
