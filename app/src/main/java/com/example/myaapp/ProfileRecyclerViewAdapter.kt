package com.example.myaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileRecyclerViewAdapter(
    private val list: List<Person>
) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val profileNameTextView = view.findViewById<TextView>(R.id.textProfileName)
        val imageProfilePicture = view.findViewById<ImageView>(R.id.imageProfilePicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.profileNameTextView.text = list[position].name
        holder.imageProfilePicture.setImageResource(list[position].res)
        holder.itemView.setOnClickListener {

        }
        holder.imageProfilePicture.setOnClickListener {

        }
    }
}