package vn.ngphong.musiccc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_playlist.view.*
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.data.models.Playlist
import vn.ngphong.musiccc.data.models.Song
import vn.ngphong.musiccc.util.Tool

class PlaylistAdapter(listPlaylists: MutableList<Playlist>) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistHolder>() {
    private var playlists = mutableListOf<Playlist>()
    private var mListener: OnClickListener? = null

    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }

    init {
        playlists = listPlaylists
        notifyDataSetChanged()
    }

    inner class PlaylistHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(playlist: Playlist) {
            itemView.playlist_txt_name.text = playlist.name
            if (playlist.name == "Create a new playlist") {
                itemView.grid_playlist_art.visibility = View.GONE
                itemView.playlist_img_play.visibility = View.GONE
                itemView.playlist_img_art.visibility = View.VISIBLE
                itemView.playlist_img_art.setImageResource(R.mipmap.ic_launcher_foreground)
            } else {
                if (playlist.songs.size < 4) {
                    itemView.grid_playlist_art.visibility = View.GONE
                    itemView.playlist_img_art.visibility = View.VISIBLE
                    val art = Tool.getTrackPicture(playlist.songs[0].data)
                    if (art != null) {
                        itemView.playlist_img_art.setImageBitmap(art)
                    } else {
                        itemView.playlist_img_art.setImageResource(R.mipmap.ic_launcher_foreground)
                    }
                } else {
                    itemView.grid_playlist_art.visibility = View.VISIBLE
                    itemView.playlist_img_art.visibility = View.GONE
                    var art = Tool.getTrackPicture(playlist.songs[0].data)
                    if (art != null) {
                        itemView.playlist_img_art1.setImageBitmap(art)
                    } else {
                        itemView.playlist_img_art1.setImageResource(R.mipmap.ic_launcher_foreground)
                    }
                    art = Tool.getTrackPicture(playlist.songs[1].data)
                    if (art != null) {
                        itemView.playlist_img_art2.setImageBitmap(art)
                    } else {
                        itemView.playlist_img_art2.setImageResource(R.mipmap.ic_launcher_foreground)
                    }
                    art = Tool.getTrackPicture(playlist.songs[2].data)
                    if (art != null) {
                        itemView.playlist_img_art3.setImageBitmap(art)
                    } else {
                        itemView.playlist_img_art3.setImageResource(R.mipmap.ic_launcher_foreground)
                    }
                    art = Tool.getTrackPicture(playlist.songs[3].data)
                    if (art != null) {
                        itemView.playlist_img_art4.setImageBitmap(art)
                    } else {
                        itemView.playlist_img_art4.setImageResource(R.mipmap.ic_launcher_foreground)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_playlist, parent, false)
        return PlaylistHolder(v)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlaylistHolder, position: Int) {
        val playlist = playlists[position]
        playlist.let { holder.bindData(it) }
        holder.itemView.setOnClickListener {
            mListener?.onPlaylistClick(position)
        }
        holder.itemView.playlist_img_play.setOnClickListener {
            mListener?.onPlayImgClick(position)
        }
    }

    fun updateData(listPlaylists: MutableList<Playlist>) {
        playlists = listPlaylists
        notifyDataSetChanged()
    }

    fun updateNewList(position: Int, newPlaylistSongs: MutableList<Song>) {
        playlists[position].songs = newPlaylistSongs
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onPlaylistClick(position: Int)
        fun onPlayImgClick(position: Int)
    }
}