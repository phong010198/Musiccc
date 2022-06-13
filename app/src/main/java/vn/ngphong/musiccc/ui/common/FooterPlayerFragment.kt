package vn.ngphong.musiccc.ui.common

import dagger.hilt.android.AndroidEntryPoint
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.base.BaseFragmentWithoutViewModel
import vn.ngphong.musiccc.databinding.FragmentFooterPlayerBinding
import vn.ngphong.musiccc.util.PlaybackListener
import vn.ngphong.musiccc.util.Tool

@AndroidEntryPoint
class FooterPlayerFragment : BaseFragmentWithoutViewModel<FragmentFooterPlayerBinding>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_footer_player

    override fun initView() {
    }

    override fun setupObserver() {
        activityViewModel.currentSong.observe(viewLifecycleOwner) {
            viewBinding.footerTxtTitle.text = it.title
            viewBinding.footerTxtArtist.text = it.artist
            viewBinding.footerTxtArtist.text = it.artist
            val art = Tool.getTrackPicture(it.data)
            if (art != null) {
                viewBinding.footerImgArt.setImageBitmap(art)
            } else {
                viewBinding.footerImgArt.setImageResource(R.mipmap.ic_launcher_foreground)
            }
        }
        activityViewModel.currentState.observe(viewLifecycleOwner) {
            viewBinding.footerImgPlayPause.setImageResource(
                if (it == PlaybackListener.State.PAUSED) R.drawable.ic_pause
                else R.drawable.ic_play
            )
        }
    }
}
