package vn.ngphong.musiccc.ui.playlist

import androidx.recyclerview.widget.LinearLayoutManager
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.ui.song.SongAdapter
import vn.ngphong.musiccc.base.BaseFragmentWithoutViewModel
import vn.ngphong.musiccc.databinding.FragmentShowTracksBinding

class PlaylistTracksFragment : BaseFragmentWithoutViewModel<FragmentShowTracksBinding>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_show_tracks

    private lateinit var playlistTracksAdapter: SongAdapter

    override fun initView() {
        playlistTracksAdapter =
            SongAdapter(mutableListOf(), true, object : SongAdapter.TrackCallback {
                override fun onTrackClick(positionTrack: Int) {
                    //TODO("Not yet implemented")
                }

                override fun onMenuClick(positionTrack: Int) {
                    //TODO("Not yet implemented")
                }
            })
        viewBinding.recyclerAlbumTracks.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setupObserver() {
        //TODO("Not yet implemented")
    }
}