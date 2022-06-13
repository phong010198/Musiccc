package vn.ngphong.musiccc.ui.album

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.*
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.base.BaseFragmentWithoutViewModel
import vn.ngphong.musiccc.data.models.Album
import vn.ngphong.musiccc.databinding.FragmentAlbumsBinding

class AlbumsFragment : BaseFragmentWithoutViewModel<FragmentAlbumsBinding>() {
    companion object {
        fun newInstance(): AlbumsFragment {
            val args = Bundle()
            val fragment = AlbumsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_albums

    private lateinit var albumAdapter: AlbumAdapter

    override fun initView() {
        activityViewModel.getAlbums()
        viewBinding.imvBack.setOnClickListener { parentFragmentManager.popBackStack() }
        viewBinding.tvBack.setOnClickListener { parentFragmentManager.popBackStack() }
        albumAdapter = AlbumAdapter(mutableListOf(), object : AlbumAdapter.AlbumCallback {
            override fun onItemClick(position: Int) {
                val albumTracksFragment = AlbumTracksFragment()
                val bundle = Bundle()
                bundle.putInt("position", position)
                albumTracksFragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left,
                        R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right
                    )
                    .add(
                        R.id.layout_main,
                        albumTracksFragment,
                        AlbumTracksFragment::class.java.name
                    ).addToBackStack(AlbumTracksFragment::class.java.name).commit()
            }
        })
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.justifyContent = JustifyContent.SPACE_AROUND
        layoutManager.alignItems = AlignItems.CENTER
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        viewBinding.rcvAlbums.layoutManager = layoutManager
        viewBinding.rcvAlbums.adapter = albumAdapter
    }

    override fun setupObserver() {
        activityViewModel.allAlbums.observe(viewLifecycleOwner) {
            viewBinding.tvEmptyAlbums.isVisible = it.isEmpty()
            albumAdapter.updateData(it)
        }
    }
}
