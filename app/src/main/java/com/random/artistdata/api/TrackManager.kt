package com.random.artistdata.api


import com.random.artistdata.models.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming

const val GET_TRACK: String = "search"

class TrackManager {
    private val service: TrackService
    private val retrofit = RetrofitModule.providesRetrofit()

    init {
        service = retrofit.create(TrackService::class.java)
    }

    suspend fun getTracks(artistName: String) = service.getTrack(artistName)

    interface TrackService {

        @Streaming
        @GET(GET_TRACK)
        suspend fun getTrack(@Query("term") artistName: String)
                : Response<APIResponse>
    }
}