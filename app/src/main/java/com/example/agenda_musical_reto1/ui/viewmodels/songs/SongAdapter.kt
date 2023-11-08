package com.example.agenda_musical_reto1.ui.viewmodels.songs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.databinding.ListSongRowBinding
import com.example.agenda_musical_reto1.utils.YouTubeThumbnailUtil

class SongAdapter(
    private val onClickListener: (Song) -> Unit
) : ListAdapter<Song, SongAdapter.SongViewHolder>(SongDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding =
            ListSongRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song)
        holder.itemView.setOnClickListener {
             onClickListener(song)
        }
    }

    inner class SongViewHolder(private val binding: ListSongRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.songName.text = song.title
            binding.artistText.text = song.author
            YouTubeThumbnailUtil.getYouTubeThumbnailUrl(song.url)
            YouTubeThumbnailUtil.loadYouTubeThumbnail(binding.root.context, song.url, binding.songImage)
        }

    }

    class SongDiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return (oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.author == newItem.author)
        }
    }
}