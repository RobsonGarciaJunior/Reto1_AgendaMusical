package com.example.agenda_musical_reto1.ui.viewmodels.songs

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda_musical_reto1.R
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.databinding.ListSongRowBinding
import com.example.agenda_musical_reto1.utils.YouTubeThumbnailUtil

class SongAdapter(
    private val onClickListener: (Song) -> Unit,
    private val onLikeClick: (Song) -> Unit
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
        //TODO Corregir esto para que el listener sea apenas del icono del corazon ya que tal y como esta ahora da fallos extrannos en la lista de favs
        holder.itemView.findViewById<ImageButton>(R.id.favoriteImage).setOnClickListener {
            onLikeClick(song)
        }
    }

    inner class SongViewHolder(private val binding: ListSongRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.songName.text = song.title
            binding.artistText.text = song.author
            YouTubeThumbnailUtil.getYouTubeThumbnailUrl(song.url)
            YouTubeThumbnailUtil.loadYouTubeThumbnail(
                binding.root.context,
                song.url,
                binding.songImage
            )
            if (song.isFavorite) {
                binding.favoriteImage.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.favoriteImage.setImageResource(R.drawable.ic_not_favorite)
            }
        }
    }

    class SongDiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.idSong == newItem.idSong
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return (oldItem.idSong == newItem.idSong && oldItem.title == newItem.title && oldItem.author == newItem.author)
        }
    }
}