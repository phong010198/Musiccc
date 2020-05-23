package vn.ngphong.musiccc.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_player.*
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.models.Track
import vn.ngphong.musiccc.services.IPlayerHolder
import vn.ngphong.musiccc.services.MusicService
import vn.ngphong.musiccc.services.NotificationMusiccc
import vn.ngphong.musiccc.utils.PlaybackListener
import vn.ngphong.musiccc.utils.Tool
import vn.ngphong.musiccc.views.activities.MainActivity

class PlayerFragment : Fragment() {
    var musicService: MusicService? = null
    var iPlayerHolder: IPlayerHolder? = null
    var notificationMusiccc: NotificationMusiccc? = null
    var mPlaybackListener: MyPlaybackListener? = null
    var mainActivity: MainActivity? = null

    lateinit var seekBar: SeekBar
    lateinit var tvPlayerTitle: TextView
    lateinit var tvPlayerArtist: TextView
    lateinit var tvPlayerDuration: TextView
    lateinit var tvPlayerCurrentTime: TextView
    lateinit var imgPlayerArt: ImageView
    lateinit var imgPlayerPlayPause: ImageView
    var isSeeking = false

    inner class MyPlaybackListener : PlaybackListener() {
        override fun onPositionChanged(position: Int) {
            if (!isSeeking) {
                seekBar.progress = position
            }
        }

        override fun onStateChanged(state: Int) {
            if (iPlayerHolder?.getState() == State.PLAYING) {
                mainActivity!!.updateFooter()
                updateUI(iPlayerHolder!!.getCurrentTrack()!!)
            }
            if (iPlayerHolder?.getState() != State.RESUMED &&
                iPlayerHolder?.getState() != State.PAUSED
            ) {
                seekBar.progress = iPlayerHolder!!.getResumePosition()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getService()
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_player, container, false)
        seekBar = rootView.findViewById(R.id.player_seekBar)
        tvPlayerTitle = rootView.findViewById(R.id.player_tv_title)
        tvPlayerArtist = rootView.findViewById(R.id.player_tv_artist)
        tvPlayerDuration = rootView.findViewById(R.id.player_tv_duration)
        tvPlayerCurrentTime = rootView.findViewById(R.id.player_tv_currentTime)
        imgPlayerArt = rootView.findViewById(R.id.player_img_art)
        imgPlayerPlayPause = rootView.findViewById(R.id.player_img_playPause)
        return rootView
    }

    private fun updateUI(track: Track) {
        seekBar.progress = 0
        seekBar.max = track.duration.toInt()
        if (iPlayerHolder!!.isPlayerExist()) {
            seekBar.progress = iPlayerHolder!!.getCurrentPosition()
        }
        tvPlayerTitle.text = track.title
        tvPlayerArtist.text = track.artist
        tvPlayerDuration.text = Tool.formatTime(track.duration)
        val art = Tool.getTrackPicture(track.data)
        if (art != null) {
            imgPlayerArt.setImageBitmap(art)
        } else {
            imgPlayerArt.setImageResource(R.mipmap.ic_launcher_foreground)
        }
        imgPlayerPlayPause.setImageResource(R.mipmap.ic_pause_foreground)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSeekBar()
        player_img_previous.setOnClickListener {
            if (iPlayerHolder!!.isPlayerExist()) {
                iPlayerHolder!!.playPrev()
            } else {
                iPlayerHolder!!.setCurrentTrackByPos(iPlayerHolder!!.getRandomTrackPosition())
                iPlayerHolder!!.play()
            }
            updateUI(iPlayerHolder!!.getCurrentTrack()!!)
            mainActivity!!.updateFooter()
        }
        player_img_next.setOnClickListener {
            if (iPlayerHolder!!.isPlayerExist()) {
                iPlayerHolder!!.playNext()
            } else {
                iPlayerHolder!!.setCurrentTrackByPos(iPlayerHolder!!.getRandomTrackPosition())
                iPlayerHolder!!.play()
            }
            updateUI(iPlayerHolder!!.getCurrentTrack()!!)
            mainActivity!!.updateFooter()
        }
        player_img_playPause.setOnClickListener {
            @Suppress("DEPRECATION")
            if (iPlayerHolder!!.isPlaying()) {
                player_img_playPause.setImageDrawable(resources.getDrawable(R.mipmap.ic_play_foreground))
                iPlayerHolder!!.pause()
            } else {
                player_img_playPause.setImageDrawable(resources.getDrawable(R.mipmap.ic_pause_foreground))
                iPlayerHolder!!.resume()
            }
            mainActivity!!.updateFooter()
        }
    }

    override fun onResume() {
        super.onResume()
        getService()
        activity?.startService(mainActivity?.playerIntent)
        if (musicService != null) {
            updateUI(iPlayerHolder!!.getCurrentTrack()!!)
        }
        @Suppress("DEPRECATION")
        if (iPlayerHolder!!.getState() == PlaybackListener.State.PAUSED) {
            player_img_playPause.setImageDrawable(resources.getDrawable(R.mipmap.ic_play_foreground))
        } else {
            player_img_playPause.setImageDrawable(resources.getDrawable(R.mipmap.ic_pause_foreground))
        }
    }

    private fun getService() {
        val mainActivity = activity as MainActivity
        musicService = mainActivity.getService()
        if (musicService != null) {
            iPlayerHolder = musicService!!.playerHolder
            notificationMusiccc = musicService!!.notificationMusiccc
        }
        if (mPlaybackListener == null) {
            mPlaybackListener = MyPlaybackListener()
            iPlayerHolder?.setPlaybackListener(mPlaybackListener!!)
        }
    }

    private fun setupSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var userPos = 0
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    userPos = progress
                }
                tvPlayerCurrentTime.text = Tool.formatTime(progress.toLong())
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