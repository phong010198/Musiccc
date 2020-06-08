package vn.ngphong.musiccc.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    }
}