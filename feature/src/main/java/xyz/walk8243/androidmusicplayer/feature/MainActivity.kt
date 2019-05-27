package xyz.walk8243.androidmusicplayer.feature

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import java.io.File
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private val log = Logger.getLogger(this::class.java.name)
//    private val STORAGE_REQUEST_CODE = 101
    private var audioPlayer: AudioPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayer?.destroy()
    }

    private fun setupPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            log.info("Permission to storage denied")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)
        } else {
            init()
        }
    }

    private fun init() {
        initializeUI()
        initializeAudioPlayer()
    }

    private fun initializeUI() {
        log.info("initializeUI")
//        logFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path)
        log.info(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path)
//        var files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).listFiles()
        val files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).listFiles()
        val message = if (files == null) {
            "ファイルがありません"
        } else {
            for (file in files) {
                log.info(file.name + ": " + file.absoluteFile)
            }
            "ファイルがありました"
        }
        val mTextView = findViewById<TextView>(R.id.message)
        mTextView.text = message
    }

    private fun initializeAudioPlayer() {
        audioPlayer = AudioPlayer(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1000 -> {
                log.info(permissions.joinToString(",") + "; " + grantResults.size.toString() + "; " + grantResults[0])
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    log.info("Permission has been denied by user")
                } else {
                    log.info("Permission has been granted by user")
                    init()
                }
            }
        }
    }
}
