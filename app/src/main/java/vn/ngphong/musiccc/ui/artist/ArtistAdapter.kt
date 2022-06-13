package vn.ngphong.musiccc.ui.artist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.ngphong.musiccc.data.models.Artist
import vn.ngphong.musiccc.databinding.ItemArtistBinding
import vn.ngphong.musiccc.ui.song.SongAdapter

class ArtistAdapter(private var data: MutableList<Artist>, var callback: ArtistCallback) :
    RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    interface ArtistCallback {
        fun onTrackClick(positionTrack: Int, positionArtist: Int)
    }

    var viewPool = RecyclerView.RecycledViewPool()

    inner class ViewHolder(val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.artistTvName.text = data[position].name
            val artistTrackAdapter = SongAdapter(data[position].songs, true,
                object : SongAdapter.TrackCallback {
                    override fun onTrackClick(positionTrack: Int) {
                        callback.onTrackClick(positionTrack, holder.absoluteAdapterPosition)
                    }

                    override fun onMenuClick(positionTrack: Int) {
                        //TODO("Not yet implemented")
                    }

                })
            binding.recyclerArtistTracks.apply {
                layoutManager = LinearLayoutManager(binding.recyclerArtistTracks.context)
                adapter = artistTrackAdapter
                setRecycledViewPool(viewPool)
            }
            binding.recyclerArtistTracks.isVisible = data[position].expand
            binding.root.setOnClickListener {
                data[position].expand = !data[position].expand
                notifyItemChanged(position)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: MutableList<Artist>) {
        this.data = data
        notifyDataSetChanged()
    }
}