package com.example.myaapp.note_app_api_based

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myaapp.R
import com.example.myaapp.note_app.local_db.Note
import com.example.myaapp.note_app_api_based.api.NoteApi

class NotesApiAdapter(val list: List<NoteApi>) :
    RecyclerView.Adapter<NotesApiAdapter.ViewHolder>() {
    class ViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        val headerTitle = layout.findViewById<TextView>(R.id.headerTitle)
        val description = layout.findViewById<TextView>(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_custom_view, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.headerTitle.text = list[position].title
        holder.description.text = list[position].content
    }
}