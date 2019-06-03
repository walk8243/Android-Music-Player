package xyz.walk8243.androidmusicplayer.feature

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import java.util.logging.Logger

class MusicItem : TextView {
    private val log = Logger.getLogger(this::class.java.name)
    var filepath: String = ""

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setPadding(0, 5, 0, 5)
        textSize = 18F
        isClickable = true
        setOnClickListener {
            log.info("click is $filepath")
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left + 10, top, right + 10, bottom)
    }
}