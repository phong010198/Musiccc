package vn.ngphong.musiccc.ui.playlist

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.base.BaseFragmentWithoutViewModel
import vn.ngphong.musiccc.databinding.FragmentPlaylistsBinding

class PlaylistsFragment : BaseFragmentWithoutViewModel<FragmentPlaylistsBinding>() {
    companion object {
        fun newInstance(): PlaylistsFragment {
            val args = Bundle()
            val fragment = PlaylistsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_playlists

    private lateinit var playlistAdapter: PlaylistAdapter

    override fun initView() {
        viewBinding.imvBack.setOnClickListener { parentFragmentManager.popBackStack() }
        viewBinding.tvBack.setOnClickListener { parentFragmentManager.popBackStack() }
        playlistAdapter =
            PlaylistAdapter(mutableListOf(), object : PlaylistAdapter.PlaylistCallback {
                override fun onCreatePlaylist() {
                    //TODO("Not yet implemented")
                }

                override fun onPlaylistClick(position: Int) {
                    //TODO("Not yet implemented")
                }
            })
        viewBinding.rcvPlaylists.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rcvPlaylists.adapter = playlistAdapter
    }

    override fun setupObserver() {
    }
}