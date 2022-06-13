package vn.ngphong.musiccc.ui.album

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.ngphong.musiccc.R
import vn.ngphong.musiccc.data.models.Album
import vn.ngphong.musiccc.databinding.ItemAlbumBinding
import vn.ngphong.musiccc.util.DoAsync
import vn.ngphong.musiccc.util.Tool

class AlbumAdapter(
    private var data: MutableList<Album>,
    var callback: AlbumCallback
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    interface AlbumCallback {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.albumTvName.text = data[position].name
            binding.albumTvCountTracks.text =
                "${data[position].songs.size} track" + if (data[position].songs.size > 1) "s" else " only"
            if (data[position].songs.size < 4) {
                binding.albumGroupImgArt.visibility = View.GONE
                binding.albumImgArt.visibility = View.VISIBLE
                DoAsync {
                    val art = Tool.getTrackPicture(data[position].songs[0].data)
                    holder.binding.root.post {
                        if (art != null) {
                            binding.albumImgArt.setImageBitmap(art)
                        } else {
                            binding.albumImgArt.setImageResource(R.drawable.ic_play)
                        }
                    }
                }
            } else {
                binding.albumGroupImgArt.visibility = View.VISIBLE
                binding.albumImgArt.visibility = View.GONE
                DoAsync {
                    val art1 = Tool.getTrackPicture(data[position].songs[0].data)
                    val art2 = Tool.getTrackPicture(data[position].songs[1].data)
                    val art3 = Tool.getTrackPicture(data[position].songs[2].data)
                    val art4 = Tool.getTrackPicture(data[position].songs[3].data)
                    holder.binding.root.post {
                        if (art1 != null) {
                            binding.albumImgArt1.setImageBitmap(art1)
                        } else {
                            binding.albumImgArt1.setImageResource(R.drawable.ic_play)
                        }
                        if (art2 != null) {
                            binding.albumImgArt2.setImageBitmap(art2)
                        } else {
                            binding.albumImgArt2.setImageResource(R.drawable.ic_play)
                        }
                        if (art3 != null) {
                            binding.albumImgArt3.setImageBitmap(art3)
                        } else {
                            binding.albumImgArt3.setImageResource(R.drawable.ic_play)
                        }
                        if (art4 != null) {
                            binding.albumImgArt4.setImageBitmap(art4)
                        } else {
                            binding.albumImgArt4.setImageResource(R.drawable.ic_play)
                        }
                    }
                }
            }
            binding.root.setOnClickListener { callback.onItemClick(position) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: MutableList<Album>) {
        this.data = data
        notifyDataSetChanged()
    }
}