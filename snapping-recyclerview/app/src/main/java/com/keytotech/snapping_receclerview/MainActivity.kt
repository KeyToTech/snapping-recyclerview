package com.keytotech.snapping_receclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4,5, 6, 7,8 ,9)
        val viewManager = LinearLayoutManager(this)
        val viewAdapter = SnapAdapter(list, this)
        val itemPicker = findViewById<SnappingRecyclerView>(R.id.item_picker).apply {
            setHasFixedSize(true)
            viewManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = viewManager
            /***
             * TODO: set item width
             */
            adapter = viewAdapter
        }
    }
}
