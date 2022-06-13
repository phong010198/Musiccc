package vn.ngphong.musiccc.ui.album

import androidx.recyclerview.widget.LinearLayoutManager
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.ui.song.SongAdapter
import vn.ngphong.musiccc.base.BaseFragmentWithoutViewModel
import vn.ngphong.musiccc.databinding.FragmentShowTracksBinding

class AlbumTracksFragment : BaseFragmentWithoutViewModel<FragmentShowTracksBinding>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_show_tracks

    private lateinit var albumTracksAdapter: SongAdapter
    private var position = 0

    override fun initView() {
        val bundle = this.arguments
        if (bundle != null) {
            position = bundle.getInt("position")
        }
        viewBinding.albumImgBack.setOnClickListener { parentFragmentManager.popBackStack() }
        viewBinding.albumTracksTxtName.text = activityViewModel.allAlbums.value!![position].name
        albumTracksAdapter =
            SongAdapter(
                activityViewModel.allAlbums.value!![position].songs,
                true,
                object : SongAdapter.TrackCallback {
                    override fun onTrackClick(positionTrack: Int) {
                        //TODO("Not yet implemented")
                    }

                    override fun onMenuClick(positionTrack: Int) {
                        //TODO("Not yet implemented")
                    }

                })
        viewBinding.recyclerAlbumTracks.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerAlbumTracks.adapter = albumTracksAdapter
    }

    override fun setupObserver() {
    }
}
