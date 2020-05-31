package vn.ngphong.musiccc.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.adapters.TrackAdapter
import vn.ngphong.musiccc.models.Track
import vn.ngphong.musiccc.services.IPlayerHolder
import vn.ngphong.musiccc.services.MusicService
import vn.ngphong.musiccc.services.NotificationMusiccc
import vn.ngphong.musiccc.utils.PlaybackListener
import vn.ngphong.musiccc.views.activities.MainActivity

class AlbumTracksFragment : Fragment() {
    var musicService: MusicService? = null
    var iPlayerHolder: IPlayerHolder? = null
    var notificationMusiccc: NotificationMusiccc? = null
    var mPlaybackListener: MyPlaybackListener? = null
    var mainActivity: MainActivity? = null

    private var listAlbumTracks = mutableListOf<Track>()
    private var albumTracksAdapter: TrackAdapter? = null
    private lateinit var recyclerAlbumTracks: RecyclerView

    inner class MyPlaybackListener : PlaybackListener() {
        override fun onStateChanged(state: Int) {
            if (iPlayerHolder?.getState() == State.PLAYING) {
                mainActivity!!.updateFooter()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getService()
        mainActivity = activity as MainActivity
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_album_tracks, container, false)
        val manager = LinearLayoutManager(requireContext())
        recyclerAlbumTracks = rootView.findViewById(R.id.recycler_album_tracks)
        recyclerAlbumTracks.layoutManager = manager
        albumTracksAdapter = TrackAdapter(listAlbumTracks)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerAlbumTracks.adapter = albumTracksAdapter
        albumTracksAdapter!!.setOnClickListener(object : TrackAdapter.OnClickListener {
            override fun onTrackClick(position: Int) {
                iPlayerHolder!!.updateTracks(listAlbumTracks, position)
                iPlayerHolder!!.play()
                mainActivity!!.updateFooter()
            }

            override fun onPlusMenuClick(position: Int) {
                Toast.makeText(
                    this@AlbumTracksFragment.context,
                    "OK$position",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}
