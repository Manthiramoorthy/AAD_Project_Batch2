package com.example.myaapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycler_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val list = listOf(
            Person(
                "Rohith",
                R.drawable.nature
            ),
            Person(
                "Fateh",
                R.drawable.success
            ),
            Person(
                "Moorthy",
                R.drawable.ic_launcher_background
            ),
            Person(
                "Rohith",
                R.drawable.nature
            ),
            Person(
                "Fateh",
                R.drawable.success
            ),
            Person(
                "Moorthy",
                R.drawable.ic_launcher_background
            ),
            Person(
                "Rohith",
                R.drawable.nature
            ),
            Person(
                "Fateh",
                R.drawable.success
            ),
            Person(
                "Moorthy",
                R.drawable.ic_launcher_background
            ),
            Person(
                "Rohith",
                R.drawable.nature
            ),
            Person(
                "Fateh",
                R.drawable.success
            ),
            Person(
                "Moorthy",
                R.drawable.ic_launcher_background
            ),
            Person(
                "Rohith",
                R.drawable.nature
            ),
            Person(
                "Fateh",
                R.drawable.success
            ),
            Person(
                "Moorthy",
                R.drawable.ic_launcher_background
            ),
            Person(
                "Moorthy",
                R.drawable.ic_launcher_background
            ),
            Person(
                "Rohith",
                R.drawable.nature
            ),
            Person(
                "Fateh",
                R.drawable.success
            ),
            Person(
                "Moorthy",
                R.drawable.ic_launcher_background
            )
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = ProfileRecyclerViewAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }
}