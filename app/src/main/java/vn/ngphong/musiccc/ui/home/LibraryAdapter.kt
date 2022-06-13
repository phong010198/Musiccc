package vn.ngphong.musiccc.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.ngphong.musiccc.databinding.ItemLibraryBinding

class LibraryAdapter(
    private var data: MutableList<String>,
    var callback: LibraryCallback
) : RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    interface LibraryCallback {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(val binding: ItemLibraryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.tvItemLibrary.text = data[position]
            binding.root.setOnClickListener { callback.onItemClick(position) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: MutableList<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: String) {
        data.add(item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.size)
    }
}