package com.example.alarmapp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.alarmapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.commit {
            replace(binding.fragment.id, TimerFragment())
        }
    }
}