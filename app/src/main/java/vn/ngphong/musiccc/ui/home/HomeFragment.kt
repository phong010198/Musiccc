package vn.ngphong.musiccc.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.*
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.base.BaseFragmentWithoutViewModel
import vn.ngphong.musiccc.data.models.Song
import vn.ngphong.musiccc.databinding.FragmentHomeBinding
import vn.ngphong.musiccc.ui.album.AlbumsFragment
import vn.ngphong.musiccc.ui.artist.ArtistsFragment
import vn.ngphong.musiccc.ui.playlist.PlaylistsFragment
import vn.ngphong.musiccc.ui.song.SongsFragment
import kotlin.random.Random

class HomeFragment : BaseFragmentWithoutViewModel<FragmentHomeBinding>() {
    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home

    private lateinit var libraryAdapter: LibraryAdapter
    private lateinit var randomSongAdapter: RandomSongAdapter

    override fun initView() {
        val listLibrary = mutableListOf(
            resources.getString(R.string.songs),
            resources.getString(R.string.artists),
            resources.getString(R.string.albums),
            resources.getString(R.string.playlists)
        )
        libraryAdapter = LibraryAdapter(listLibrary, object : LibraryAdapter.LibraryCallback {
            val transaction = parentFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left,
                R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right
            )

            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> transaction.replace(
                        R.id.layout_main,
                        SongsFragment.newInstance(),
                        SongsFragment::class.java.name
                    ).addToBackStack(SongsFragment::class.java.name).commit()

                    1 -> transaction.replace(
                        R.id.layout_main,
                        ArtistsFragment.newInstance(),
                        ArtistsFragment::class.java.name
                    ).addToBackStack(ArtistsFragment::class.java.name).commit()

                    2 -> transaction.replace(
                        R.id.layout_main,
                        AlbumsFragment.newInstance(),
                        AlbumsFragment::class.java.name
                    ).addToBackStack(AlbumsFragment::class.java.name).commit()

                    3 -> transaction.replace(
                        R.id.layout_main,
                        PlaylistsFragment.newInstance(),
                        PlaylistsFragment::class.java.name
                    ).addToBackStack(PlaylistsFragment::class.java.name).commit()
                }
            }
        })
        viewBinding.rcvHomeLibrary.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rcvHomeLibrary.adapter = libraryAdapter
        randomSongAdapter =
            RandomSongAdapter(mutableListOf(), object : RandomSongAdapter.RandomSongCallback {
                override fun onItemClick(positionTrack: Int) {
                    //TODO("Not yet implemented")
                }
            })
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.justifyContent = JustifyContent.SPACE_AROUND
        layoutManager.alignItems = AlignItems.STRETCH
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        viewBinding.rcvHomeRandom.layoutManager = layoutManager
        viewBinding.rcvHomeRandom.adapter = randomSongAdapter
    }

    override fun setupObserver() {
        activityViewModel.allSongs.observe(viewLifecycleOwner) { list ->
            val listRandomSong = mutableListOf<Song>()
            val random = List(if (list.size > 8) 8 else list.size) { Random.nextInt(0, list.size) }
            for (i in random) listRandomSong.add(list[i])
            randomSongAdapter.updateData(listRandomSong)
        }
    }
}