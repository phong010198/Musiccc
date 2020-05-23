package vn.ngphong.musiccc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_track.view.*
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.models.Track
import vn.ngphong.musiccc.utils.Tool

class TrackAdapter(listTrack: MutableList<Track>?) :
    RecyclerView.Adapter<TrackAdapter.TrackHolder>() {
    private var tracks: MutableList<Track>? = null
    private var mListener: OnClickListener? = null
    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }

    init {
        tracks = listTrack
        notifyDataSetChanged()
    }

    inner class TrackHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(track: Track) {
            itemView.track_tv_title.text = track.title
            itemView.track_tv_artist.text = track.artist
            itemView.track_tv_duration.text = Tool.formatTime(track.duration)
            val art = Tool.getTrackPicture(track.data)
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
        return tracks?.size ?: 0
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val track = tracks?.get(position)
        track.let { holder.bindData(it!!) }
        holder.itemView.setOnClickListener {
            mListener?.onTrackClick(position)
        }
        holder.itemView.track_btn_menu.setOnClickListener {
            mListener?.onPlusMenuClick(position)
        }
    }

    fun updateData(listTracks: MutableList<Track>) {
        tracks = listTracks
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onTrackClick(position: Int)
        fun onPlusMenuClick(position: Int)
    }
}