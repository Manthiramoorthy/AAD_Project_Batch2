package com.example.myaapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.listView)

        val list = listOf("Rohith", "Fateh", "Moorthy", "Kabir", "Lalitha", "Anubhav")
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        val adapter = ProfileAdapter(list)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, index, l ->
            Toast.makeText(this,  "Clicked " + list[index], Toast.LENGTH_LONG).show()
        }
    }
}

data class Person(
    private val name: String,
    private val imageRes

){

}