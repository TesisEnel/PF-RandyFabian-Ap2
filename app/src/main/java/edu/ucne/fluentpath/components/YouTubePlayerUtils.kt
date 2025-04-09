package edu.ucne.fluentpath.components


import java.util.regex.Pattern

object YouTubePlayerUtils {
    /**
     * Extrae el ID de un video de YouTube desde una URL.
     * Soporta múltiples formatos de URLs de YouTube.
     */
    fun extractYouTubeId(url: String): String? {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/|youtube.com\\/live\\/|shorts\\/|v\\/|e\\/|embed\\?video_id=|\\?v=|\\&v=|%2Fvideos%2F|watch\\?v%3D)([^#\\&\\?\\n]*)"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(url)
        return if (matcher.find()) matcher.group() else null
    }
}