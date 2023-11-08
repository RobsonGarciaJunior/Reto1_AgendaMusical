package com.example.agenda_musical_reto1.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.agenda_musical_reto1.R
import java.util.regex.Matcher
import java.util.regex.Pattern

object YouTubeThumbnailUtil {

    fun loadYouTubeThumbnail(context: Context, youTubeUrl: String, imageView: ImageView) {
        val thumbnailUrl = getYouTubeThumbnailUrl(youTubeUrl)
        Glide.with(context)
            .load(thumbnailUrl)
            .error(R.drawable.ic_song_image_not_found)
            .into(imageView)
    }

    fun getYouTubeThumbnailUrl(youTubeUrl: String): String {
        val videoId = getYouTubeVideoId(youTubeUrl)
        return "https://img.youtube.com/vi/$videoId/0.jpg"
    }

    private fun getYouTubeVideoId(youTubeUrl: String): String {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern: Pattern = Pattern.compile(pattern)
        val matcher: Matcher = compiledPattern.matcher(youTubeUrl)
        return if (matcher.find()) {
            matcher.group()
        } else {
            "error"
        }
    }
}
