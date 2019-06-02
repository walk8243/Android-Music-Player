package xyz.walk8243.androidmusicplayer.feature

import android.content.Context
import android.media.MediaPlayer
import java.util.logging.Logger

class AudioPlayer(context: Context) {
    private var target: Context
    private val log = Logger.getLogger(this::class.java.name)
    private var mediaPlayer: MediaPlayer? = null

    init {
        log.fine("AudioPlayer")
        target = context
    }

    fun create(filepath: String) {
        log.fine("AudioPlayer create")
        if(mediaPlayer != null) {
            destroy()
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(filepath)
        mediaPlayer?.prepare()
        start()
    }
    fun destroy() {
        log.fine("AudioPlayer destroy")
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun start() {
        log.fine("AudioPlayer start")
        if(mediaPlayer?.isPlaying!!) {
            log.info("isPlaying")
            return
        }
        mediaPlayer?.start()
    }

    fun stop() {
        log.fine("AudioPlayer stop")
        mediaPlayer?.stop()
    }
}