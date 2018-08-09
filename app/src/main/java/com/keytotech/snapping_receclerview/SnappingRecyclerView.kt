package com.keytotech.snapping_receclerview

import android.support.v7.widget.RecyclerView
import android.content.Context
import android.R.attr.x
import android.graphics.Point
import android.view.Display
import android.view.WindowManager
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet


/**
 * Created by Marta Turchyniak on 8/9/18.
 */
class SnappingRecyclerView : RecyclerView {

    var padding: Int = 0
    var itemWidth: Int = 0
    var allPixels: Int = 0

    constructor(context: Context): super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?){
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        //itemWidth = layoutManager.width
        padding = (size.x - itemWidth) / 2

        setOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    calculatePositionAndScroll(recyclerView)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                allPixels += dx
            }
        })
    }


    private fun calculatePositionAndScroll(recyclerView: RecyclerView) {
        var expectedPosition = (allPixels + padding - 200) / 200
        // Special cases for the padding items
        if (expectedPosition == -1) {
            expectedPosition = 0
        } else if (expectedPosition >= recyclerView.adapter.itemCount - 2) {
            expectedPosition--
        }
        scrollListToPosition(recyclerView, expectedPosition)
    }

    private fun scrollListToPosition(recyclerView: RecyclerView, expectedPosition: Int) {
        val targetScrollPos = expectedPosition * 200 + 200 - padding
        val missingPx = targetScrollPos - allPixels
        if (missingPx != 0) {
            recyclerView.smoothScrollBy(missingPx, 0)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)

    }
}