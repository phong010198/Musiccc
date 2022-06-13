package vn.ngphong.musiccc.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.ngphong.musiccc.BR
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.base.BaseActivity
import vn.ngphong.musiccc.data.local.DataLoader
import vn.ngphong.musiccc.databinding.ActivityMainBinding
import vn.ngphong.musiccc.services.IPlayerHolder
import vn.ngphong.musiccc.services.MusicService
import vn.ngphong.musiccc.services.NotificationMusiccc
import vn.ngphong.musiccc.ui.home.HomeFragment
import vn.ngphong.musiccc.util.MusicPreference
import vn.ngphong.musiccc.util.PlaybackListener
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: MainViewModel by viewModels()

    var musicService: MusicService? = null
    var iPlayerHolder: IPlayerHolder? = null
    var notificationMusiccc: NotificationMusiccc? = null
    lateinit var playerIntent: Intent

    private var serviceBound = false

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            musicService = (service as MusicService.MusicBinder).service
            iPlayerHolder = musicService!!.playerHolder
            notificationMusiccc = musicService!!.notificationMusiccc
            onServiceConnected()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
        }
    }

    override fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        viewModel.loadAllSongs(applicationContext)
        binSer()
        musicPreference = MusicPreference(this)
        supportFragmentManager.beginTransaction()
            .add(R.id.layout_main, HomeFragment.newInstance(), HomeFragment::class.java.name)
            .addToBackStack(HomeFragment::class.java.name).commit()
    }

    override fun setupObserver() {
        viewModel.currentSongList.observe(this) {
            iPlayerHolder!!.updateTracks(it, 0)
        }
        viewModel.currentSongPos.observe(this) {
            iPlayerHolder!!.setCurrentTrackByPos(it)
        }
    }

    private var firstPlay = true
    private var mPlaybackListener: MyPlaybackListener? = null
    private var musicPreference: MusicPreference? = null

    inner class MyPlaybackListener : PlaybackListener() {
        override fun onStateChanged(state: Int) {
            if (iPlayerHolder?.getState() == State.PLAYING) {
                updateFooter()
            }
            if (iPlayerHolder?.getState() != State.RESUMED &&
                iPlayerHolder?.getState() != State.PAUSED
            ) {
                updateFooter()
            }
        }
    }

    private fun onServiceConnected() {
        if (mPlaybackListener == null) {
            mPlaybackListener = MyPlaybackListener()
            iPlayerHolder!!.setPlaybackListener(mPlaybackListener!!)
        }
    }

    override fun onStart() {
        super.onStart()
        if (mPlaybackListener == null) {
            mPlaybackListener = MyPlaybackListener()
            iPlayerHolder?.setPlaybackListener(mPlaybackListener!!)
        }
    }

    override fun onPause() {
        super.onPause()
        unbindSer()
    }

    override fun onResume() {
        super.onResume()
        binSer()
        if (musicService != null) {
            updateFooter()
        }
    }

    override fun onBackPressed() {
        when (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name) {
            HomeFragment::class.java.name -> {
                val intent = Intent()
                intent.action = Intent.ACTION_MAIN
                intent.addCategory(Intent.CATEGORY_HOME)
                startActivity(intent)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun playPrev() {
        if (iPlayerHolder?.isPlayerExist()!! && !firstPlay) {
            iPlayerHolder!!.playPrev()
            updateFooter()
        }
    }

    private fun resumePause() {
        if (firstPlay) {
            iPlayerHolder!!.initPlayer()
            iPlayerHolder!!.updateTracks(DataLoader(this).querySongs(), 0)
            iPlayerHolder!!.setRandomTrackPos()
            iPlayerHolder!!.play()
        } else iPlayerHolder!!.resumePause()
        updateFooter()
    }

    private fun playNext() {
        if (iPlayerHolder?.isPlayerExist()!! && !firstPlay) {
            iPlayerHolder!!.playNext()
            updateFooter()
        }
    }

    fun updateFooter() {
        firstPlay = false
        viewModel.currentSong.value = iPlayerHolder!!.getCurrentTrack()
    }

    private fun binSer() {
        playerIntent = Intent(this, MusicService::class.java)
        playerIntent.action = ""
        bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        startService(playerIntent)
        serviceBound = true
    }

    private fun unbindSer() {
        if (serviceBound) {
            unbindService(serviceConnection)
            serviceBound = false
        }
    }

    fun getService(): MusicService? {
        return if (musicService != null) musicService!!
        else null
    }
}