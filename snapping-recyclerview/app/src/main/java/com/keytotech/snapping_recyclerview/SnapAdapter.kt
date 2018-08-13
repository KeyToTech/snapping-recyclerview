package com.keytotech.snapping_recyclerview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.keytotech.snapping_receclerview.R


/**
 * Created by Marta Turchyniak on 8/9/18.
 */
class SnapAdapter(private val list: MutableList<Int>, private val context: Context):
        RecyclerView.Adapter<SnapAdapter.ViewHolder>(){

    lateinit var recyclerView: SnappingRecyclerView
    var expectedPosition: Int = -1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = list[position].toString()
        if(position == expectedPosition){
            holder.item.setBackgroundColor(context.resources.getColor(R.color.colorPrimaryDark))
        } else{
            holder.item.setBackgroundColor(context.resources.getColor(R.color.colorAccent))
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.test_item, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var text: TextView = itemView.findViewById(R.id.text)
        var item: ConstraintLayout = itemView.findViewById(R.id.item)
    }
}