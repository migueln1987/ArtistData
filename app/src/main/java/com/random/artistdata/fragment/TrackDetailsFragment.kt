package com.random.artistdata.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.random.artistdata.databinding.FragmentTrackDetailsBinding
import com.random.artistdata.viewmodel.TrackViewModel
import kotlinx.android.synthetic.main.activity_main.*


class TrackDetailsFragment : Fragment() {

    val viewModel by activityViewModels<TrackViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrackDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        requireActivity().toolbar.title = viewModel.liveDataArtistName.value?.toUpperCase()
        return binding.root
    }

}