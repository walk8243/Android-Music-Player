package xyz.walk8243.androidmusicplayer.feature

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import java.util.logging.Logger

class MusicItemAdapter(private val musicData: ArrayList<HashMap<String, String>>) : RecyclerView.Adapter<MusicItemAdapter.MusicItemViewHolder>() {
    private val log = Logger.getLogger(this::class.java.name)
    private var musicPlayer: AudioPlayer? = null
    private var selectedHolder: MusicItemViewHolder? = null

    class MusicItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val layout: LinearLayout = view.findViewById(R.id.music_item)
        val textView: TextView = view.findViewById(R.id.music_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicItemAdapter.MusicItemViewHolder {
        log.info("viewType is $viewType")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item, parent, false)
        return MusicItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicItemViewHolder, position: Int) {
        log.info("$position is ~${musicData[position]}~ ${musicData.size}")
        holder.textView.text = musicData[position]["name"]
        holder.textView.isClickable = true
        holder.textView.setOnClickListener {
            if (holder == selectedHolder) {
                return@setOnClickListener
            }
            musicPlayer?.create(musicData[position]["path"]!!)
            if (selectedHolder != null) {
                selectedHolder!!.layout.isSelected = false
            }
            holder.layout.isSelected = true
            selectedHolder = holder
        }
    }

    override fun getItemCount() = musicData.size

    fun setMusicPlayer(audioPlayer: AudioPlayer) {
        musicPlayer = audioPlayer
    }
}