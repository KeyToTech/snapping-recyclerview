package com.keytotech.snapping_recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.keytotech.snapping_receclerview.R


class MainActivity : AppCompatActivity(), SnappingRecyclerView.InteractionListener {

    private lateinit var viewAdapter: SnapAdapter
    private lateinit var itemPicker: SnappingRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4,5, 6, 7,8 ,9)
//        val list = mutableListOf(1)
        initAdapter(list)
    }

    private fun initAdapter(list: MutableList<Int>) {
        val viewManager = LinearLayoutManager(this)
        viewAdapter = SnapAdapter(list, this)
        val itemWidth = resources.getDimension(R.dimen.item_width)
        val paddingItemWidth = resources.getDimension(R.dimen.padding_item_width)
        itemPicker = findViewById<SnappingRecyclerView>(R.id.item_picker).apply {
            setHasFixedSize(true)
            viewManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = viewManager
            viewAdapter.recyclerView = this
            adapter = viewAdapter
            initDimensions(itemWidth, paddingItemWidth, this@MainActivity)
            initFirstItem(list, 1)
            setListeners(list)
        }
    }

    override fun onItemSelected(position: Int, previousCalculatedPosition: Int) {
        viewAdapter.expectedPosition = position
        viewAdapter.notifyDataSetChanged()
        /**
         * TODO: replace with notifyitemchanged
         */
    }

    private fun SnappingRecyclerView.setListeners(list: MutableList<Int>) {
        setOnClickListener { v ->
            val itemPosition = getChildLayoutPosition(v)
            scrollListToPosition(itemPosition, list.size)
            adapter.notifyDataSetChanged()
            /**
             * TODO: replace with notifyitemchanged
             */
        }
    }

    private fun SnappingRecyclerView.initFirstItem(list: MutableList<Int>, position: Int) {
        scrollListToPosition(position, list.size)
        viewAdapter.expectedPosition = position
        viewAdapter.notifyItemChanged(position)
    }

    override fun onResume() {
        super.onResume()
        itemPicker.initDisplay()
    }
}
