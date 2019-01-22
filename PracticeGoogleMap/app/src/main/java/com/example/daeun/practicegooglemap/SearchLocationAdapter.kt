package com.example.daeun.practicegooglemap

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class SearchLocationAdapter(val context: Context, val items: ArrayList<SearchLocationItem>): RecyclerView.Adapter<SearchLocationAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_location, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position], context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val locationText = itemView?.findViewById<TextView>(R.id.text_search_location_recycler)

        fun bind (items: SearchLocationItem, context: Context) {
            locationText?.text = items.location
        }
    }
}