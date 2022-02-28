package vn.ngphong.musiccc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_track.view.*
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.data.models.Song
import vn.ngphong.musiccc.util.Tool

class TrackAdapter(listSong: MutableList<Song>?, showPlusButton: Boolean) :
    RecyclerView.Adapter<TrackAdapter.TrackHolder>() {
    private var songs: MutableList<Song>? = null
    private var showPlusButton = false
    private var mListener: OnClickListener? = null
    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }

    init {
        songs = listSong
        this.showPlusButton = showPlusButton
        notifyDataSetChanged()
    }

    inner class TrackHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(song: Song) {
            if (!showPlusButton) {
                itemView.track_btn_menu.visibility = View.GONE
            }
            itemView.track_tv_title.text = song.title
            itemView.track_tv_artist.text = song.artist
            itemView.track_tv_duration.text = Tool.formatTime(song.duration)
            val art = Tool.getTrackPicture(song.data)
            if (art != null) {
                itemView.track_img_art.setImageBitmap(art)
            } else {
                itemView.track_img_art.setImageResource(R.mipmap.ic_launcher_foreground)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TrackHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track, parent, false)
        return TrackHolder(v)
    }

    override fun getItemCount(): Int {
        return songs?.size ?: 0
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val track = songs?.get(position)
        track.let { holder.bindData(it!!) }
        holder.itemView.setOnClickListener {
            mListener?.onTrackClick(position)
        }
        holder.itemView.track_btn_menu.setOnClickListener {
            mListener?.onPlusMenuClick(position)
        }
    }

    fun updateData(songs: MutableList<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    fun addTrack(song: Song) {
        songs!!.add(song)
        songs!!.sortBy { it.title }
        notifyDataSetChanged()
    }

    fun removeTrack(position: Int) {
        songs!!.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, songs!!.size)
    }

    interface OnClickListener {
        fun onTrackClick(position: Int)
        fun onPlusMenuClick(position: Int)
    }
}