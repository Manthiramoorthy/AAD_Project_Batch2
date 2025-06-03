package com.example.myaapp.note_app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myaapp.R
import com.example.myaapp.note_app.common.Constant.CONTENT_KEY
import com.example.myaapp.note_app.common.Constant.LIST_ITEM_VALUE
import com.example.myaapp.note_app.common.Constant.SOURCE_KEY
import com.example.myaapp.note_app.common.Constant.TITLE_KEY
import com.example.myaapp.note_app.data.Note
import com.example.myaapp.note_app.ui.NoteDetailsActivity

class NotesAdapter(private val list: List<Note>): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val headerTitle = view.findViewById<TextView>(R.id.headerTitle)
        val description = view.findViewById<TextView>(R.id.description)
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
        holder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, NoteDetailsActivity::class.java)
            intent.putExtra(TITLE_KEY, list[position].title)
            intent.putExtra(CONTENT_KEY, list[position].content)
            intent.putExtra(SOURCE_KEY, LIST_ITEM_VALUE)
            view.context.startActivity(intent)
        }
    }
}