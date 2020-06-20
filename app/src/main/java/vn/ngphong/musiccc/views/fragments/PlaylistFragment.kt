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
import kotlinx.android.synthetic.main.fragment_playlist.*
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.adapters.PlaylistAdapter
import vn.ngphong.musiccc.data.DataLoader
import vn.ngphong.musiccc.models.Playlist

class PlaylistFragment : BaseFragment() {
    private var listPlaylists = mutableListOf<Playlist>()
    private var playlistAdapter: PlaylistAdapter? = null
    private lateinit var recyclerPlaylist: RecyclerView

    override fun onServiceConnected() {
        initData()
    }

    private fun initData() {
        listPlaylists = DataLoader(requireContext()).genPlaylist()
        if ((listPlaylists.isNotEmpty())) {
            playlistAdapter!!.updateData(listPlaylists)
            tv_empty_playlists.visibility = View.GONE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binSer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_playlist, container, false)
        val manager = LinearLayoutManager(requireContext())
        recyclerPlaylist = rootView.findViewById(R.id.recycler_playlists)
        recyclerPlaylist.layoutManager = manager
        playlistAdapter = PlaylistAdapter(listPlaylists)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerPlaylist.adapter = playlistAdapter
        playlistAdapter!!.setOnClickListener(object : PlaylistAdapter.OnClickListener {
            override fun onPlaylistClick(position: Int) {
                if (position == 0) {
                    Toast.makeText(
                        this@PlaylistFragment.context,
                        "Create!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    val gson = Gson()
                    val gsonString = gson.toJson(listPlaylists[position].tracks)
                    val playlistTracksFrag = PlaylistTracksFragment()
                    val bundle = Bundle()
                    bundle.putString("playlist.bundle", gsonString)
                    playlistTracksFrag.arguments = bundle
                    val playlistTracksTag = "playlistTracksFrag.tag"
                    mainActivity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.full_frame, playlistTracksFrag, playlistTracksTag)
                        .addToBackStack(null)
                        .commit()
                    mainActivity!!.hideView()
                }
            }

            override fun onPlayImgClick(position: Int) {
                iPlayerHolder!!.updateTracks(listPlaylists[position].tracks, 0)
                iPlayerHolder!!.play()
                mainActivity!!.updateFooter()
            }
        })
    }
}