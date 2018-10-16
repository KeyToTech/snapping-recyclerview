package com.keytotech.snapping_recyclerview

import android.content.Context
import android.graphics.Point
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager


/**
 * Created by Marta Turchyniak on 8/9/18.
 */
class SnappingRecyclerView : RecyclerView {

    private var padding: Float = 0f
    private var itemWidth: Float = -1f
    var allPixels: Float = 0f
    private var paddingItemWidth: Float = 0f
    private var previousCalculatedPosition: Int = -1
    private lateinit var size: Point
    private lateinit var listener: InteractionListener
    private val mHandler = Handler()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        initDisplay()
        initListeners()
    }

    private fun SnappingRecyclerView.initListeners() {
        initLayoutChangeListener()
        initScrollListener()
    }

    fun initDimensions(itemWidth: Float, paddingItemWidth: Float, listener: InteractionListener) {
        this.itemWidth = itemWidth
        this.paddingItemWidth = paddingItemWidth
        this.listener = listener
        padding = (size.x - itemWidth) / 2
    }

    private fun scrollToView(child: View?) {
        child?.let {
            stopScroll()
            val scrollDistance = getScrollDistance(child)
            if (scrollDistance != 0)
                smoothScrollBy(scrollDistance, 0)
        }
    }

    private fun getScrollDistance(child: View): Int {
        val itemWidth = getChildAt(0).measuredWidth
        val centerX = measuredWidth / 2
        val childCenterX = child.x.toInt() + itemWidth / 2
        return childCenterX - centerX
    }


    private fun SnappingRecyclerView.initScrollListener() {
        setOnScrollListener(object : OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    scrollToView(getCenterView())
                    notifyItemSelected(calculateSelectedPosition())
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                allPixels += dx
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun initLayoutChangeListener() {
        addOnLayoutChangeListener(object : OnLayoutChangeListener {
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                if (left == oldLeft && right == oldRight && top == oldTop && bottom == oldBottom) {
                    removeOnLayoutChangeListener(this)
                    mHandler.postDelayed({
                        scrollToView(getChildAt(0))
                        notifyItemSelected(0)
                    }, 20)
                }
            }
        })
    }

    private fun notifyItemSelected(position: Int) {
        listener.onItemSelected(position, previousCalculatedPosition)
        previousCalculatedPosition = position
    }

    fun scrollListToPosition(position: Int, listSize: Int) {
        val pos: Int = checkPosition(position, listSize)
        scrollListToPosition(this, pos)
    }

    private fun checkPosition(position: Int, listSize: Int): Int {
        return if (position == listSize) {
            position
        } else {
            position + 1
        }
    }

    fun initDisplay() {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        size = Point()
        display.getSize(size)
    }

    private fun scrollListToPosition(recyclerView: RecyclerView, expectedPosition: Int) {
        val targetScrollPos = expectedPosition * itemWidth + paddingItemWidth - padding
        val missingPx = (targetScrollPos - allPixels).toInt()
        if (missingPx != 0) {
            recyclerView.smoothScrollBy(missingPx, 0)
        }
    }

    private fun calculateSelectedPosition(): Int {
        val layoutManager = layoutManager as LinearLayoutManager
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
        return (lastVisiblePosition - firstVisiblePosition) / 2 + firstVisiblePosition
    }

    private fun getChildClosestToPosition(x: Int): View? {
        if (childCount <= 0)
            return null
        val itemWidth = getChildAt(0).measuredWidth
        var closestX = 9999
        var closestChild: View? = null
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childCenterX = child.x.toInt() + itemWidth / 2
            val xDistance = childCenterX - x
            /** if child center is closer than previous closest, set it as closest   */
            if (Math.abs(xDistance) < Math.abs(closestX)) {
                closestX = xDistance
                closestChild = child
            }
        }
        return closestChild
    }

    private fun getCenterView(): View? {
        return getChildClosestToPosition(measuredWidth / 2)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
    }

    interface InteractionListener {
        fun onItemSelected(position: Int, previousCalculatedPosition: Int)
    }
}