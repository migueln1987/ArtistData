package com.random.artistdata.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.random.artistdata.R
import com.random.artistdata.adapter.TrackListAdapter
import com.random.artistdata.databinding.FragmentTrackListBinding
import com.random.artistdata.utils.setDivider
import com.random.artistdata.viewmodel.TrackViewModel
import kotlinx.android.synthetic.main.activity_main.*


class TrackListFragment : Fragment() {

    val viewModel by activityViewModels<TrackViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrackListBinding.inflate(inflater, container, false)

        binding.apply {
            this.viewModel = this@TrackListFragment.viewModel
            this.lifecycleOwner = this@TrackListFragment
        }
        binding.recyclerView.setDivider(R.drawable.recycler_view_divider)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().toolbar.title = viewModel.liveDataArtistName.value?.toUpperCase()
        viewModel.adapter = TrackListAdapter {
            viewModel.setSelectedTrack(it)
            findNavController().navigate(R.id.action_trackListFragment_to_trackDetailsFragment)
        }
    }

}