package xyz.walk8243.androidmusicplayer.feature

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private val log = Logger.getLogger(this::class.java.name)
    private var audioPlayer: AudioPlayer? = null
    private val musicItemData: ArrayList<HashMap<String, String>> = arrayListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MusicItemAdapter
    private lateinit var viewManager: LinearLayoutManager

    companion object {
        const val PERMISSION_REQUEST_CODE = 8243
        private val musicExtensions = arrayOf("mp3")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.audio_list)
        viewAdapter = MusicItemAdapter(musicItemData)
        viewManager = LinearLayoutManager(this)

        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter

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
        val files = MusicFiles(null).getFilesRecursively(null)
        val dirPathStrLength = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath.length
        if (files.size > 0) {
            for (file in files) {
                if (musicExtensions.contains(file.extension)) {
                    val fileInfo = hashMapOf("name" to file.nameWithoutExtension, "dir" to file.parent.substring(dirPathStrLength), "path" to file.absolutePath)
                    musicItemData.add(fileInfo)
                    viewAdapter.notifyItemChanged(musicItemData.lastIndex)
                }
            }
        } else {
            val mTextView = findViewById<TextView>(R.id.textView)
            mTextView.text = "ファイルがありません"
            mTextView.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun testingMusicItem() {
        findViewById<TextView>(R.id.textView).text = ""
        for (i in 0..4) {
            val fileInfo = hashMapOf("name" to "Index is $i", "dir" to "FileDir is $i", "path" to "Filepath is $i")
            musicItemData.add(fileInfo)
            viewAdapter.notifyItemChanged(musicItemData.lastIndex)
        }
    }

    private fun initializeAudioPlayer() {
        val audioButton: Button = findViewById(R.id.audio_button)
        audioPlayer = AudioPlayer(this, audioButton)
        viewAdapter.setMusicPlayer(audioPlayer!!)
        audioButton.setOnClickListener {
            if (audioButton.isEnabled) {
                if (audioButton.isSelected) {
                    audioPlayer?.pause()
                } else {
                    audioPlayer?.resume()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    log.info("Permission has been denied by user")
                    testingMusicItem()
                } else {
                    log.info("Permission has been granted by user")
                    throughPermissions()
                }
            }
        }
    }
}
