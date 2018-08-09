package com.keytotech.snapping_receclerview

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context

/**
 * Created by Marta Turchyniak on 8/9/18.
 */
class SnapAdapter(private val list: MutableList<Int>, val  context: Context): RecyclerView.Adapter<SnapAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("rrrrrrrr", "binding")
        holder.text.text = list[position].toString()
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("rrrrrrrr", "create")
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.test_item, parent, false)
        return  ViewHolder(v)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var text = itemView.findViewById<TextView>(R.id.text)!!

    }

    fun addAll(list: MutableList<Int>) {
        this.list.addAll(list)
        notifyItemRangeInserted(this.list.size, list.size)
    }
}