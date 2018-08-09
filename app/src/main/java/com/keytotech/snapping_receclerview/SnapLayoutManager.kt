package com.keytotech.snapping_receclerview

import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.view.ViewGroup
import android.content.Context
import android.icu.text.RelativeDateTimeFormatter

/**
 * Created by Marta Turchyniak on 8/9/18.
 */
class SnapLayoutManager(val context: Context): RecyclerView.LayoutManager() {

     var currentPosition: Int = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}
