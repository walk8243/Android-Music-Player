package xyz.walk8243.androidmusicplayer.feature

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text
import java.util.logging.Logger

class MusicItemAdapter(private val musicData: ArrayList<HashMap<String, String>>) : RecyclerView.Adapter<MusicItemAdapter.MusicItemViewHolder>() {
    private val log = Logger.getLogger(this::class.java.name)
    private var musicPlayer: AudioPlayer? = null
    private var selectedHolder: MusicItemViewHolder? = null

    class MusicItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val layout: LinearLayout = view.findViewById(R.id.music_item)
        val musicName: TextView = view.findViewById(R.id.music_title)
        val musicDir: TextView = view.findViewById(R.id.music_dir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicItemAdapter.MusicItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item, parent, false)
        return MusicItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicItemViewHolder, position: Int) {
        holder.musicName.text = musicData[position]["name"]
        holder.musicDir.text = if (!musicData[position]["dir"].isNullOrEmpty()) {
            musicData[position]["dir"]!!.substring(1).replace("/", " > ")
        } else ""
        holder.layout.isClickable = true
        holder.layout.setOnClickListener {
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