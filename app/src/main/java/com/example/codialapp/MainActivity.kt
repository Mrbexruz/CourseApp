package com.example.codialapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.example.codialapp.databinding.ActivityMainBinding
import com.example.codialapp.fragments.Courses.CoursesFragment
import com.example.codialapp.fragments.Groups.GroupsFragment
import com.example.codialapp.fragments.HomeFragment
import com.example.codialapp.fragments.mentors.MentorsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

    }
}