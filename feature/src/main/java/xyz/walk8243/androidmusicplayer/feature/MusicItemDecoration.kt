package xyz.walk8243.androidmusicplayer.feature

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import java.util.logging.Logger

class MusicItemDecoration : RecyclerView.ItemDecoration() {
    private val log = Logger.getLogger(this::class.java.name)
    private var lineStyle: Paint? = null

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter == null) return
        val itemCount = parent.adapter!!.itemCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        if (itemCount == 0) return
        for (i in 1 .. (itemCount-1)) {
            val child = parent.getChildAt(i-1)
            val bottom = child.bottom

            drawHorizontalLine(c, left, bottom, right, bottom)
        }
        super.onDraw(c, parent, state)
    }

    private fun drawHorizontalLine(c: Canvas, x1: Int, y1: Int, x2: Int, y2: Int) {
        configLineStyle()
        c.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), lineStyle!!)
    }

    private fun configLineStyle() {
        lineStyle = Paint().apply {
            strokeWidth = 2F
            color = Color.GRAY
        }
    }
}