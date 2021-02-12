package com.random.artistdata.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.random.artistdata.adapter.TrackListAdapter
import com.random.artistdata.api.TrackManager
import com.random.artistdata.models.TrackDTO
import com.random.artistdata.utils.SingleLiveEvent
import com.random.artistdata.utils.getStringResponseFromRaw
import kotlinx.coroutines.launch

class TrackViewModel() : ViewModel() {
    val data: ArrayList<TrackDTO> = ArrayList()
    var adapter: TrackListAdapter? = null
    private var _selectedTrackData: MutableLiveData<TrackDTO> = MutableLiveData()
    var selectedTrackData: LiveData<TrackDTO>? = _selectedTrackData

    var liveDataArtistName: MutableLiveData<String> = MutableLiveData()
    var liveDataArtistNameError: MutableLiveData<String> = MutableLiveData()
    val navigateToListView: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var progressVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private var trackManager = TrackManager()

    @VisibleForTesting
    constructor(trackManager: TrackManager) : this() {
        this.trackManager = trackManager
    }


    fun searchByArtist() {
        val artistName: String? = liveDataArtistName.value
        if (artistName?.isNotBlank() == true) {
            progressVisibility.value = true
            viewModelScope.launch {

                val response = trackManager.getTracks(artistName)
                val bodyData = response.body()?.results
                if (response.isSuccessful && bodyData != null) {
                    data.clear()
                    data.addAll(ArrayList(bodyData))
                    navigateToListView.postValue(true)
                } else {
                    val errorResponse = response.errorBody()?.let {
                        getStringResponseFromRaw(it)
                    }
                    liveDataArtistNameError.postValue(errorResponse ?: "Error Response")
                }
                progressVisibility.value = false
            }
        } else {
            liveDataArtistNameError.postValue("Enter Artist Name")
            progressVisibility.value = false
        }
    }

    fun setSelectedTrack(trackDataDTO: TrackDTO) {
        _selectedTrackData.value = trackDataDTO
    }
}