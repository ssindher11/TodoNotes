package com.ssindher11.todonotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.clicklisteners.ItemClickListener
import com.ssindher11.todonotes.model.Notes

class NotesAdapter(val list: List<Notes>, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes: Notes = list[position]
        val title = notes.title
        val description = notes.description

        holder.titleTV.text = title
        holder.descTV.text = description
        holder.itemView.setOnClickListener { itemClickListener.onClick(notes) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.tv_title)
        val descTV: TextView = itemView.findViewById(R.id.tv_description)
    }
}