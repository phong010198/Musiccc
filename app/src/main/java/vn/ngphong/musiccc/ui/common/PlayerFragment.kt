package vn.ngphong.musiccc.ui.common

import android.content.Context
import android.os.Build
import android.widget.SeekBar
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.base.BaseFragmentWithoutViewModel
import vn.ngphong.musiccc.data.models.Song
import vn.ngphong.musiccc.databinding.FragmentPlayerBinding
import vn.ngphong.musiccc.services.IPlayerHolder
import vn.ngphong.musiccc.services.MusicService
import vn.ngphong.musiccc.services.NotificationMusiccc
import vn.ngphong.musiccc.ui.MainActivity
import vn.ngphong.musiccc.util.PlaybackListener
import vn.ngphong.musiccc.util.Tool

class PlayerFragment : BaseFragmentWithoutViewModel<FragmentPlayerBinding>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_player

    var mainActivity: MainActivity? = null
    private var musicService: MusicService? = null
    var iPlayerHolder: IPlayerHolder? = null
    private var notificationMusiccc: NotificationMusiccc? = null
    var isSeeking = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getService()
        mainActivity = activity as MainActivity
    }

    override fun initView() {
        setupSeekBar()
        viewBinding.playerImgPrevious.setOnClickListener {
            if (iPlayerHolder!!.isPlayerExist()) {
                iPlayerHolder!!.playPrev()
            } else {
                iPlayerHolder!!.setRandomTrackPos()
                iPlayerHolder!!.play()
            }
            updateUI(iPlayerHolder!!.getCurrentTrack()!!)
            mainActivity!!.updateFooter()
        }
        viewBinding.playerImgNext.setOnClickListener {
            if (iPlayerHolder!!.isPlayerExist()) {
                iPlayerHolder!!.playNext()
            } else {
                iPlayerHolder!!.setRandomTrackPos()
                iPlayerHolder!!.play()
            }
            updateUI(iPlayerHolder!!.getCurrentTrack()!!)
            mainActivity!!.updateFooter()
        }
        viewBinding.playerImgPlayPause.setOnClickListener {
            if (iPlayerHolder!!.isPlaying()) {
                viewBinding.playerImgPlayPause.setImageResource(R.drawable.ic_play)
                iPlayerHolder!!.pause()
            } else {
                viewBinding.playerImgPlayPause.setImageResource(R.drawable.ic_pause)
                iPlayerHolder!!.resume()
            }
            mainActivity!!.updateFooter()
        }
        viewBinding.playerImgShuffle.setOnClickListener {
            if (iPlayerHolder!!.getShuffleState()) viewBinding.playerImgShuffle.clearColorFilter()
            else viewBinding.playerImgShuffle.setColorFilter(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColor(
                    R.color.colorPrimary,
                    null
                )
                else resources.getColor(R.color.colorPrimary)
            )
            iPlayerHolder!!.changeShuffleState()
        }
        viewBinding.playerImgRepeat.setOnClickListener {
            when (iPlayerHolder!!.getRepeatState()) {
                0 -> viewBinding.playerImgRepeat.setImageResource(R.drawable.ic_repeat)
                1 -> viewBinding.playerImgRepeat.setImageResource(R.drawable.ic_repeat)
                2 -> viewBinding.playerImgRepeat.setImageResource(R.drawable.ic_repeat)
            }
            iPlayerHolder!!.changeRepeatState()
        }
    }

    override fun setupObserver() {
    }

    private fun updateUI(song: Song) {
        viewBinding.playerSeekBar.progress = 0
        viewBinding.playerSeekBar.max = song.duration.toInt()
        if (iPlayerHolder!!.isPlayerExist()) {
            viewBinding.playerSeekBar.progress = iPlayerHolder!!.getCurrentPosition()!!
        }
        viewBinding.playerTvTitle.text = song.title
        viewBinding.playerTvArtist.text = song.artist
        viewBinding.playerTvDuration.text = Tool.formatTime(song.duration)
        val art = Tool.getTrackPicture(song.data)
        if (art != null) {
            viewBinding.playerImgArt.setImageBitmap(art)
        } else {
            viewBinding.playerImgArt.setImageResource(R.mipmap.ic_launcher_foreground)
        }
        viewBinding.playerImgPlayPause.setImageResource(R.drawable.ic_play)
    }

    override fun onResume() {
        super.onResume()
        getService()
        activity?.startService(mainActivity?.playerIntent)
        if (musicService != null) {
            updateUI(iPlayerHolder!!.getCurrentTrack()!!)
        }
        if (iPlayerHolder!!.getState() == PlaybackListener.State.PAUSED) {
            viewBinding.playerImgPlayPause.setImageResource(R.drawable.ic_play)
        } else {
            viewBinding.playerImgPlayPause.setImageResource(R.drawable.ic_pause)
        }
        if (iPlayerHolder!!.getShuffleState()) {
            viewBinding.playerImgShuffle.clearColorFilter()
        } else {
            viewBinding.playerImgShuffle.setColorFilter(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColor(
                    R.color.colorPrimary,
                    null
                )
                else resources.getColor(R.color.colorPrimary)
            )

        }
        when (iPlayerHolder!!.getRepeatState()) {
            0 -> viewBinding.playerImgRepeat.setImageResource(R.drawable.ic_repeat)
            1 -> viewBinding.playerImgRepeat.setImageResource(R.drawable.ic_repeat)
            2 -> viewBinding.playerImgRepeat.setImageResource(R.drawable.ic_repeat)
        }
    }

    private fun getService() {
        val mainActivity = activity as MainActivity
        musicService = mainActivity.getService()
        if (musicService != null) {
            iPlayerHolder = musicService!!.playerHolder
            notificationMusiccc = musicService!!.notificationMusiccc
        }
    }

    private fun setupSeekBar() {
        viewBinding.playerSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            var userPos = 0
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    userPos = progress
                }
                viewBinding.playerTvCurrentTime.text = Tool.formatTime(progress.toLong())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isSeeking = false
                iPlayerHolder!!.seek(userPos)
            }
        })
    }
}