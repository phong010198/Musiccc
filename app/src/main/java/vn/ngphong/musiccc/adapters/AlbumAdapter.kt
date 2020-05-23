package vn.ngphong.musiccc.adapters

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_album.view.*
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.models.Album

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
            if (BitmapFactory.decodeFile(album.tracks[0].albumArt) != null)
                itemView.album_img_art.setImageBitmap(BitmapFactory.decodeFile(album.tracks[0].albumArt))
            else
                itemView.album_img_art.setImageResource(R.mipmap.ic_launcher_foreground)
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