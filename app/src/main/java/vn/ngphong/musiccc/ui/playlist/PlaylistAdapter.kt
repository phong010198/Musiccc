package vn.ngphong.musiccc.ui.playlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.data.models.Playlist
import vn.ngphong.musiccc.data.models.Song
import vn.ngphong.musiccc.databinding.ItemPlaylistBinding
import vn.ngphong.musiccc.util.DoAsync
import vn.ngphong.musiccc.util.Tool

class PlaylistAdapter(private var data: MutableList<Playlist>, val callback: PlaylistCallback) :
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    interface PlaylistCallback {
        fun onCreatePlaylist()
        fun onPlaylistClick(position: Int)
    }

    inner class ViewHolder(val binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.playlistTvName.text = data[position].name
            if (data[position].name == "Create a new playlist") {
                binding.albumGroupImgArt.visibility = View.GONE
                binding.playlistImgPlay.visibility = View.GONE
                binding.playlistImgArt.visibility = View.VISIBLE
                binding.playlistImgArt.setImageResource(R.mipmap.ic_launcher_foreground)
                binding.root.setOnClickListener { callback.onCreatePlaylist() }
            } else {
                if (data[position].songs.size < 4) {
                    binding.albumGroupImgArt.visibility = View.GONE
                    binding.playlistImgArt.visibility = View.VISIBLE
                    DoAsync {
                        val art = Tool.getTrackPicture(data[position].songs[0].data)
                        holder.binding.root.post {
                            if (art != null) {
                                binding.playlistImgArt.setImageBitmap(art)
                            } else {
                                binding.playlistImgArt.setImageResource(R.drawable.ic_play)
                            }
                        }
                    }
                } else {
                    binding.albumGroupImgArt.visibility = View.VISIBLE
                    binding.playlistImgArt.visibility = View.GONE
                    DoAsync {
                        val art1 = Tool.getTrackPicture(data[position].songs[0].data)
                        val art2 = Tool.getTrackPicture(data[position].songs[1].data)
                        val art3 = Tool.getTrackPicture(data[position].songs[2].data)
                        val art4 = Tool.getTrackPicture(data[position].songs[3].data)
                        holder.binding.root.post {
                            if (art1 != null) {
                                binding.playlistImgArt1.setImageBitmap(art1)
                            } else {
                                binding.playlistImgArt1.setImageResource(R.drawable.ic_play)
                            }
                            if (art2 != null) {
                                binding.playlistImgArt2.setImageBitmap(art2)
                            } else {
                                binding.playlistImgArt2.setImageResource(R.drawable.ic_play)
                            }
                            if (art3 != null) {
                                binding.playlistImgArt3.setImageBitmap(art3)
                            } else {
                                binding.playlistImgArt3.setImageResource(R.drawable.ic_play)
                            }
                            if (art4 != null) {
                                binding.playlistImgArt4.setImageBitmap(art4)
                            } else {
                                binding.playlistImgArt4.setImageResource(R.drawable.ic_play)
                            }
                        }
                    }
                }
                binding.root.setOnClickListener { callback.onPlaylistClick(position) }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: MutableList<Playlist>) {
        this.data = data
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNewList(position: Int, newPlaylistSongs: MutableList<Song>) {
        data[position].songs = newPlaylistSongs
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onPlaylistClick(position: Int)
        fun onPlayImgClick(position: Int)
    }
}