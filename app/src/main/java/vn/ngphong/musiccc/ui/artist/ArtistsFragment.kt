package vn.ngphong.musiccc.ui.artist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.base.BaseFragmentWithoutViewModel
import vn.ngphong.musiccc.data.models.Artist
import vn.ngphong.musiccc.databinding.FragmentArtistsBinding

class ArtistsFragment : BaseFragmentWithoutViewModel<FragmentArtistsBinding>() {
    companion object {
        fun newInstance(): ArtistsFragment {
            val args = Bundle()
            val fragment = ArtistsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_artists

    private lateinit var artistAdapter: ArtistAdapter

    override fun initView() {
        activityViewModel.getArtists()
        viewBinding.imvBack.setOnClickListener { parentFragmentManager.popBackStack() }
        viewBinding.tvBack.setOnClickListener { parentFragmentManager.popBackStack() }
        artistAdapter = ArtistAdapter(mutableListOf(), object : ArtistAdapter.ArtistCallback {
            override fun onTrackClick(positionTrack: Int, positionArtist: Int) {
                //TODO("Not yet implemented")
            }
        })
        viewBinding.rcvArtists.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rcvArtists.adapter = artistAdapter
    }

    override fun setupObserver() {
        activityViewModel.allArtists.observe(viewLifecycleOwner) {
            viewBinding.tvEmptyArtists.isVisible = it.isEmpty()
            artistAdapter.updateData(it)
        }
    }
}
