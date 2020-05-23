package vn.ngphong.musiccc.views.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import vn.ngphong.musiccc.services.IPlayerHolder
import vn.ngphong.musiccc.services.MusicService
import vn.ngphong.musiccc.services.NotificationMusiccc

abstract class BaseActivity : PermissionActivity() {
    var musicService: MusicService? = null
    var iPlayerHolder: IPlayerHolder? = null
    var notificationMusiccc: NotificationMusiccc? = null
    lateinit var playerIntent: Intent

    var serviceBound = false

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            musicService = (service as MusicService.MusicBinder).service
            iPlayerHolder = musicService!!.playerHolder
            notificationMusiccc = musicService!!.notificationMusiccc
            this@BaseActivity.onServiceConnected()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
        }
    }

    abstract fun onServiceConnected()
    fun binSer() {
        playerIntent = Intent(this, MusicService::class.java)
        playerIntent.action = ""
        bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        startService(playerIntent)
        serviceBound = true
    }

    fun unbindSer() {
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
