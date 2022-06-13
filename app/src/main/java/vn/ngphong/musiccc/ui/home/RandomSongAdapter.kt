package vn.ngphong.musiccc.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.data.models.Song
import vn.ngphong.musiccc.databinding.ItemSongLargeBinding
import vn.ngphong.musiccc.util.DoAsync
import vn.ngphong.musiccc.util.Tool

class RandomSongAdapter(
    private var data: MutableList<Song>,
    var callback: RandomSongCallback
) : RecyclerView.Adapter<RandomSongAdapter.ViewHolder>() {

    interface RandomSongCallback {
        fun onItemClick(positionTrack: Int)
    }

    inner class ViewHolder(val binding: ItemSongLargeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSongLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.tvName.text = data[position].title
            binding.tvArtist.text = data[position].artist
            DoAsync {
                val art = Tool.getTrackPicture(data[position].data)
                holder.binding.root.post {
                    if (art != null) {
                        binding.imvArt.setImageBitmap(art)
                    } else {
                        binding.imvArt.setImageResource(R.drawable.ic_play)
                    }
                }
            }
            binding.root.setOnClickListener { callback.onItemClick(position) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: MutableList<Song>) {
        this.data = data
        notifyDataSetChanged()
    }
}