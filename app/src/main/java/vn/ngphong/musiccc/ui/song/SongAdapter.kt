package vn.ngphong.musiccc.ui.song

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.data.models.Song
import vn.ngphong.musiccc.databinding.ItemSongListBinding
import vn.ngphong.musiccc.util.DoAsync
import vn.ngphong.musiccc.util.Tool

class SongAdapter(
    private var data: MutableList<Song>,
    private val showAddButton: Boolean,
    var callback: TrackCallback
) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    interface TrackCallback {
        fun onTrackClick(positionTrack: Int)
        fun onMenuClick(positionTrack: Int)
    }

    inner class ViewHolder(val binding: ItemSongListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.trackBtnMenu.isVisible = showAddButton
            binding.trackTvTitle.text = data[position].title
            binding.trackTvArtist.text = data[position].artist
            binding.trackTvDuration.text = Tool.formatTime(data[position].duration)
            DoAsync {
                val art = Tool.getTrackPicture(data[position].data)
                holder.binding.root.post {
                    if (art != null) {
                        binding.trackImgArt.setImageBitmap(art)
                    } else {
                        binding.trackImgArt.setImageResource(R.drawable.ic_play)
                    }
                }
            }
            binding.root.setOnClickListener { callback.onTrackClick(position) }
            if (showAddButton) {
                binding.trackBtnMenu.setOnClickListener { callback.onMenuClick(position) }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: MutableList<Song>) {
        this.data = data
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTrack(song: Song) {
        data.add(song)
        data.sortBy { it.title }
        notifyDataSetChanged()
    }

    fun removeTrack(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.size)
    }
}