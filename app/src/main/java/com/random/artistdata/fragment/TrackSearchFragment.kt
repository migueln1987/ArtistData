package com.random.artistdata.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.random.artistdata.R
import com.random.artistdata.databinding.FragmentArtistSearchBinding
import com.random.artistdata.viewmodel.TrackViewModel

class TrackSearchFragment : Fragment() {

    private val viewModel by activityViewModels<TrackViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentArtistSearchBinding =
            FragmentArtistSearchBinding.inflate(inflater, container, false)
        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }
        viewModel.navigateToListView.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(R.id.action_trackSearchFragment_to_trackListFragment)
                viewModel.navigateToListView.value = false
            }
            viewModel.progressVisibility.value = false
        })

        viewModel.liveDataArtistName.observe(viewLifecycleOwner, {
            viewModel.progressVisibility.value = false
            viewModel.liveDataArtistNameError.value = ""
        })
        return binding.root
    }

}