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

        //todo fix navigation
        supportFragmentManager.commit {
            replace(binding.fragment.id, TimerFragment())
        }

        binding.bottomNav.setOnNavigationItemReselectedListener {
            setFragment(it.itemId)
        }
    }

    private fun setFragment(itemId: Int) {
        val fragment = when(itemId) {
            R.id.TimerFragment -> TimerFragment()
            else -> SettingsFragment()
        }
        supportFragmentManager.commit {
            replace(binding.fragment.id, fragment)
        }
    }
}