package xyz.walk8243.androidmusicplayer.feature

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import java.util.logging.Logger

class AudioPlayer(context: Context) {
    private var target: Context
    private val log = Logger.getLogger(this::class.java.name)
    private var mediaPlayer: MediaPlayer? = null

    init {
        log.fine("AudioPlayer")
        target = context
    }

    fun create() {
        log.fine("AudioPlayer create")
        mediaPlayer = MediaPlayer.create(target, Uri.parse(""))
        start()
    }
    fun destroy() {
        log.fine("AudioPlayer destroy")
        stop()
        mediaPlayer = null
    }

    fun start() {
        log.fine("AudioPlayer start")
        mediaPlayer?.start()
    }

    fun stop() {
        log.fine("AudioPlayer stop")
        mediaPlayer?.release()
    }
}