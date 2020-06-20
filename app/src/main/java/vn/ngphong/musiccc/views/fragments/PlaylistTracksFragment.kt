package vn.ngphong.musiccc.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_playlist_tracks.*
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.adapters.TrackAdapter
import vn.ngphong.musiccc.models.Track
import java.lang.reflect.Type

class PlaylistTracksFragment : BaseFragment() {
    private var listTracksString: String? = null
    private var listPlaylistTracks = mutableListOf<Track>()
    private var playlistTracksAdapter: TrackAdapter? = null
    private lateinit var recyclerPlaylistTracks: RecyclerView

    override fun onServiceConnected() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binSer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_playlist_tracks, container, false)
        listTracksString = arguments?.getString("playlist.bundle")
        stringToList()
        val manager = LinearLayoutManager(requireContext())
        recyclerPlaylistTracks = rootView.findViewById(R.id.recycler_playlist_tracks)
        recyclerPlaylistTracks.layoutManager = manager
        playlistTracksAdapter = TrackAdapter(listPlaylistTracks, false)
        return rootView
    }

    private fun stringToList() {
        if (listTracksString != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<MutableList<Track>>() {}.type
            val tracks = gson.fromJson<MutableList<Track>>(listTracksString, type)
            if (tracks != null && tracks.isNotEmpty()) {
                listPlaylistTracks = tracks
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerPlaylistTracks.adapter = playlistTracksAdapter
        playlistTracksAdapter!!.setOnClickListener(object : TrackAdapter.OnClickListener {
            override fun onTrackClick(position: Int) {
                iPlayerHolder!!.updateTracks(listPlaylistTracks, position)
                iPlayerHolder!!.play()
                mainActivity!!.updateFooter()
            }

            override fun onPlusMenuClick(position: Int) {

            }
        })
        playlist_tracks_img_play.setOnClickListener {
            iPlayerHolder!!.updateTracks(listPlaylistTracks, 0)
            iPlayerHolder!!.play()
            mainActivity!!.updateFooter()
        }
        playlist_img_add.setOnClickListener {
            Toast.makeText(
                this@PlaylistTracksFragment.context,
                "Add",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}