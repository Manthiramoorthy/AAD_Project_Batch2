package com.example.myaapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myaapp.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


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


        binding.recyclerView.adapter = ProfileRecyclerViewAdapter(list)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        val username = intent.getStringExtra("username");

        binding.textGreeting.text = "Hi, " + username
    }
}