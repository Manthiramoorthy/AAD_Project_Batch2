package com.example.myaapp.others.ui

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityListViewBinding
import com.example.myaapp.others.adapter.ProfileAdapter
import com.example.myaapp.others.common.Constants

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.logoutButton.setOnClickListener {
            val sharedPref = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.remove(Constants.IS_LOGGED_IN_KEY)
            editor.remove(Constants.USERNAME_KEY)
            editor.apply()
            onBackPressedDispatcher.onBackPressed()
        }



//        val list = listOf("Rohith", "Fateh", "Moorthy", "Kabir", "Lalitha", "Anubhav")
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

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
            )
        )
        val adapter = ProfileAdapter(list)
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { adapterView, view, index, l -> // listview, item view, index, view id
            Toast.makeText(this,  "Clicked " + list[index], Toast.LENGTH_LONG).show()
        }
        val username = intent.getStringExtra(Constants.USERNAME_KEY)
        Toast.makeText(this, "Userame " + username, Toast.LENGTH_LONG).show()
    }
}

class Person(
    val name: String,
    val res: Int
)