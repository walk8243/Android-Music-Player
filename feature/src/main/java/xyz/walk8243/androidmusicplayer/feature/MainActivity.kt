package xyz.walk8243.androidmusicplayer.feature

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private val log = Logger.getLogger(this::class.java.name)
    private var audioPlayer: AudioPlayer? = null

    companion object {
        const val PERMISSION_REQUEST_CODE = 8243
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermission()
        initializeAudioPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayer?.destroy()
    }

    private fun setupPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            log.info("Permission to storage denied")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        } else {
            throughPermissions()
        }
    }

    private fun throughPermissions() {
        initializeUI()
    }

    private fun initializeUI() {
        log.fine("initializeUI")
        val files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).listFiles()
        val listBox = findViewById<LinearLayout>(R.id.list)
        val mTextView = findViewById<TextView>(R.id.textView)
        mTextView.text = if (files == null) {
            "ファイルがありません"
        } else {
            for (file in files) {
                log.info(file.name + ": " + file.absoluteFile)
                val itemView = TextView(this)
                itemView.text = file.name
                listBox.addView(itemView)
            }
            ""
        }
    }

    private fun initializeAudioPlayer() {
        audioPlayer = AudioPlayer(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    log.info("Permission has been denied by user")
                } else {
                    log.info("Permission has been granted by user")
                    throughPermissions()
                }
            }
        }
    }
}
