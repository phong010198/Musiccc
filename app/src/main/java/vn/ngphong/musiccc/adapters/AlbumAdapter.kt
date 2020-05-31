package vn.ngphong.musiccc.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_album.view.*
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.models.Album
import vn.ngphong.musiccc.utils.Tool

class AlbumAdapter(listAlbums: MutableList<Album>) :
    RecyclerView.Adapter<AlbumAdapter.AlbumHolder>() {
    private var albums = mutableListOf<Album>()
    private var mListener: OnClickListener? = null

    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }

    init {
        albums = listAlbums
        notifyDataSetChanged()
    }

    inner class AlbumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindData(album: Album) {
            itemView.album_txt_name.text = album.name
            itemView.album_txt_countTracks.text =
                "${album.tracks.size} track" + if (album.tracks.size > 1) "s" else " only"
            if (album.tracks.size < 4) {
                itemView.grid_album_art.visibility = View.GONE
                itemView.album_img_art.visibility = View.VISIBLE
                val art = Tool.getTrackPicture(album.tracks[0].data)
                if (art != null) {
                    itemView.album_img_art.setImageBitmap(art)
                } else {
                    itemView.album_img_art.setImageResource(R.mipmap.ic_launcher_foreground)
                }
            } else {
                itemView.grid_album_art.visibility = View.VISIBLE
                itemView.album_img_art.visibility = View.GONE
                var art = Tool.getTrackPicture(album.tracks[0].data)
                if (art != null) {
                    itemView.album_img_art1.setImageBitmap(art)
                } else {
                    itemView.album_img_art1.setImageResource(R.mipmap.ic_launcher_foreground)
                }
                art = Tool.getTrackPicture(album.tracks[1].data)
                if (art != null) {
                    itemView.album_img_art2.setImageBitmap(art)
                } else {
                    itemView.album_img_art2.setImageResource(R.mipmap.ic_launcher_foreground)
                }
                art = Tool.getTrackPicture(album.tracks[2].data)
                if (art != null) {
                    itemView.album_img_art3.setImageBitmap(art)
                } else {
                    itemView.album_img_art3.setImageResource(R.mipmap.ic_launcher_foreground)
                }
                art = Tool.getTrackPicture(album.tracks[3].data)
                if (art != null) {
                    itemView.album_img_art4.setImageBitmap(art)
                } else {
                    itemView.album_img_art4.setImageResource(R.mipmap.ic_launcher_foreground)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return AlbumHolder(v)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        val album = albums[position]
        album.let { holder.bindData(it) }
        holder.itemView.setOnClickListener {
            mListener?.onAlbumClick(position)
        }
    }

    fun updateData(listAlbums: MutableList<Album>) {
        albums = listAlbums
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onAlbumClick(position: Int)
    }
}