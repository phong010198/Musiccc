package vn.ngphong.musiccc.ui.song

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.base.BaseFragmentWithoutViewModel
import vn.ngphong.musiccc.databinding.FragmentSongsBinding

class SongsFragment : BaseFragmentWithoutViewModel<FragmentSongsBinding>() {
    companion object {
        fun newInstance(): SongsFragment {
            val args = Bundle()
            val fragment = SongsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_songs

    private lateinit var songAdapter: SongAdapter

    override fun initView() {
        viewBinding.imvBack.setOnClickListener { parentFragmentManager.popBackStack() }
        viewBinding.tvBack.setOnClickListener { parentFragmentManager.popBackStack() }
        songAdapter = SongAdapter(mutableListOf(), true, object : SongAdapter.TrackCallback {
            override fun onTrackClick(positionTrack: Int) {
                //TODO("Not yet implemented")
            }

            override fun onMenuClick(positionTrack: Int) {
                //TODO("Not yet implemented")
            }
        })
        viewBinding.rcvSongs.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rcvSongs.adapter = songAdapter
    }

    override fun setupObserver() {
        activityViewModel.allSongs.observe(viewLifecycleOwner) {
            viewBinding.tvEmptySongs.isVisible = it.isEmpty()
            songAdapter.updateData(it)
        }
    }
}