package com.example.alarmapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.alarmapp.R
import com.example.alarmapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val timerFragment = TimerFragment()
    private val settingsFragment = SettingsFragment()

    private var activeFragment: Fragment = timerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo fix navigation
        supportFragmentManager.commit {
            add(binding.fragment.id, settingsFragment)
            hide(settingsFragment)
            add(binding.fragment.id, timerFragment)
        }

        binding.bottomNav.setOnItemSelectedListener {menuItem ->
            when(menuItem.itemId){
                R.id.TimerFragment -> {setFragment(timerFragment)}
                R.id.SettingsFragment -> {setFragment(settingsFragment)}
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment): Boolean {
        supportFragmentManager.commit {
            hide(activeFragment)
            show(fragment)
            activeFragment = fragment
        }
        return true
    }
}