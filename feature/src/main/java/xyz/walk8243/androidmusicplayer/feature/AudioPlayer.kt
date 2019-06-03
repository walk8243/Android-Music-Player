package xyz.walk8243.androidmusicplayer.feature

import android.content.Context
import android.media.MediaPlayer
import android.widget.Button
import java.util.logging.Logger

class AudioPlayer(context: Context, private val operationButton: Button) {
    private var target: Context
    private val log = Logger.getLogger(this::class.java.name)
    private var mediaPlayer: MediaPlayer? = null

    init {
        log.fine("AudioPlayer")
        target = context
        operationButton.isEnabled = false
    }

    fun create(filepath: String) {
        log.fine("AudioPlayer create")
        if(mediaPlayer != null) {
            destroy()
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(filepath)
        mediaPlayer?.prepare()
        operationButton.isEnabled = true
        start()
    }
    fun destroy() {
        log.fine("AudioPlayer destroy")
        mediaPlayer?.release()
        mediaPlayer = null
        operationButton.isEnabled = false
    }

    fun start() {
        log.fine("AudioPlayer start")
        if(mediaPlayer?.isPlaying!!) {
            log.info("isPlaying")
            return
        }
        mediaPlayer?.start()
        operationButton.isSelected = true
    }

    fun resume() {
        log.fine("AudioPlayer resume")
        mediaPlayer?.start()
        operationButton.isSelected = true
    }

    fun pause() {
        log.fine("AudioPlayer pause")
        mediaPlayer?.pause()
        operationButton.isSelected = false
    }

    fun stop() {
        log.fine("AudioPlayer stop")
        mediaPlayer?.stop()
        operationButton.isSelected = false
    }
}